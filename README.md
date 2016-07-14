# DAOBOT
- Java DAO (Data Access Object) Generics.

- Based in GORM (Grails ORM) Criteria Builder. For more information about the criteria methods [Click here](http://grails.github.io/grails-doc/3.1.x/ref/Domain%20Classes/createCriteria.html). NOTE: Some methods are not supported (yet).

- Database Queries made easy to read and to maintain.

- Removed JSON Queries support, will add in another project.

- 97% Unit Test Coverage

----------

# Installation

Add this dependenency to your pom
	<!-- https://mvnrepository.com/artifact/com.github.hguerrerojaime/daobot -->
	<dependency>
	    <groupId>com.github.hguerrerojaime</groupId>
	    <artifactId>daobot</artifactId>
	    <version>1.0</version>
	</dependency>
	
----------

# Criteria Builder (CB) Basic Usage

Find all Books where "title" like "mon%"

	@Transactional
	@Test
	public void testSingleDynamicFieldSearchWithCB(){
		
		final String dynamicField = "title";
		final String query= "mon%";
			
		ResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB()
			 .like(dynamicField,query)
		);
			
			
		assertFalse(bookResultSet.list().isEmpty());
			
	}

Find all Books where release date is today and their units bought are greater than 1000, sorted by units bought ascendent
	
	@Test
	@Transactional
	public void testCustomQuery(){
		
		ResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB()
			.eq("releaseDate",new Date())
			.ge("unitsBought",1000)
			.sort("unitsBought")
			
		,10,0);
		
		assertTrue(!bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() > 0);
		
	}
	
Find all Books where id equals 100 or title equals "I Love Grails"

	@Test
	@Transactional
	public void testAnotherCustomQuery(){
	
		final Long desiredId = 100L;
		final String desiredBookTitle = "I Love Grails";
		
		ResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB()
			.or(new CB()
				.eq("id",desiredId)
				.eq("title",desiredBookTitle)
			),10,0);
		
		assertTrue(!bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() > 0);
		
	}


###Joins	
Find all Books where authors name case-insensitive like "John D%"

	@Test
	@Transactional
	public void testAnotherCustomQuery(){
	
		final String name = "John D%";
		
		ResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB()
			//Book's Properties
			.join("author",new CB()
			    //Author's properties
				.ilike("name",name)
			),10,0);
		
		assertTrue(!bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() > 0);
		
	}
	
Find all Books where units sold greater than 1000 and authors name case-insensitive like "John D%" using left join

	@Test
	@Transactional
	public void testAnotherCustomQuery(){
	
		final String name = "John D%";

		ResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB()
			//Book's Properties
			.gt("unitsSold",1000)
			.join("author",JoinType.LEFT,new CB()
			    //Author's properties
				.ilike("name",name)
			),10,0);
		
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
