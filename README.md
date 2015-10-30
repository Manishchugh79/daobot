# DAOBOT
- Java DAO (Data Access Object) Generics.

- Based in GORM (Grails ORM) Criteria Builder. For more information about the criteria methods [Click here](http://grails.github.io/grails-doc/3.1.x/ref/Domain%20Classes/createCriteria.html). NOTE: Some methods are not supported (yet).

- Database Queries made easy to read and to maintain.

- Can transform JSON Objects into queries for Back-End/Front-End communication. Let's call it JSON Query Language, or JSQL for now (Details Below).

----------

# Criteria Builder (CB) and Filter Builder (FB) Basic Usage

Find all Books where "title" like "mon%"

	@Transactional
	@Test
	public void testSingleDynamicFieldSearchWithCB(){
		
		final String dynamicField = "title";
		final String query= "mon%";
			
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			 like(dynamicField,query);
		}});
			
			
		assertFalse(bookResultSet.list().isEmpty());
			
	}

Find all Books where release date is today and their units bought are greater than 1000, sorted by units bought ascendent
	
	@Test
	@Transactional
	public void testCustomQuery(){
		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			eq("releaseDate",new Date());
			ge("unitsBought",1000);
			
			sort("unitsBought");
			
		}},10,0);
		
		assertTrue(!bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() > 0);
		
	}
	
Find all Books where id equals 100 or title equals "I Love Grails"

	@Test
	@Transactional
	public void testAnotherCustomQuery(){
	
		final Long desiredId = 100L;
		final String desiredBookTitle = "I Love Grails";
		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
		
			or(new FB(){{
				idEq(desiredId);
				eq("title",desiredBookTitle);
			}});
			
		}},10,0);
		
		assertTrue(!bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() > 0);
		
	}
	
# Basic Structure

The Entity Object

	@Entity
	@Table(name="book")
	public class BookEO implements EntityObject<Long>{
		
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long id;
		
		@Column(name="title")
		private String title;
		
		@Column(name="description")
		private String description;
		
		@Column(name="release_date")
		private Date releaseDate;
		
		@Column(name="units_sold")
		private Integer unitsSold;
		
		@Column(name="units_bought")
		private Integer unitsBought;
		
		@ManyToOne
		@JoinColumn(name="author_id")
		private AuthorEO author;
	
		... setters and getters
		
	}
	
The DAO

	public interface BookDAO extends DAO<BookEO, Long> {
	
	}
	
The DAO Implementation

	public class BookDAOImpl extends DAOImpl<BookEO, Long> implements BookDAO {
	
		public BookDAOImpl() {
			super(BookEO.class);
		}
	
	}

# JSON Query Languaje (JSQL)

This is pretty much a JSON object that allows the use of dynamic queries through the Front-End (It uses Jackson JSON processing api).

Implementation Test

	@Test
	@Transactional
	public void testSearchAuthorWithBookCriteria() throws JsonParseException, JsonMappingException, IOException{
        
        //Load your JSON query string from somewhere
        String jsonQuery = TestUtils.loadJsonQuery("bookQuery.json"); 
        
        DResultSet<BookEO> bookRS = bookDAO.findAll(new JsQLCriteriaQuery(jsonQuery));
         
        assertTrue(bookRS.count() > 0);
    }
    
bookQuery.json

	{
		"eq": [
			["title","I Love Grails"]
		]
		
	}
	
JSQL Syntax

	{
		"condition":[
			...place all the blocks of this condition type here
			[condition arguments]
		],
		
		...optional sort if you want
		"sort":[
			["sortField1"], ..default asc
			["sortField2","desc"] order defined explicit
		]
	}

# Some JSQL Examples

Find all books where title is neither ilike (case-insensitive like) ornare% nor Vestibulum% and unitsSold are greater than 20, order by id desc

	{
		"not":[{
			"or": [{
				"ilike":[ 
					["title","ornare%"],
					["title","Vestibulum%"] 
				]
			}]
		}],
		"gt":[ ["unitsSold",20] ],
		"sort":[ ["id","desc"] ]
	}

Find all books where title like "Vestibulum%" and unitsSold equals to 0

	{
		"like":[ 
			["title","Vestibulum%"] 
		],
		"eq":[ ["unitsSold",0] ]
	}