package com.github.hguerrerojaime.daobot.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.hguerrerojaime.daobot.core.CB;
import com.github.hguerrerojaime.daobot.core.DResultSet;
import com.github.hguerrerojaime.daobot.core.FB;
import com.github.hguerrerojaime.daobot.test.dao.AuthorDAO;
import com.github.hguerrerojaime.daobot.test.dao.BookDAO;
import com.github.hguerrerojaime.daobot.test.eo.AuthorEO;
import com.github.hguerrerojaime.daobot.test.eo.BookEO;
import com.github.hguerrerojaime.daobot.test.utils.TestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/META-INF/applicationContext.xml" 
})
public class BookDAOTest {
	
	private final Logger log = Logger.getLogger(BookDAOTest.class);
	
	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private AuthorDAO authorDAO;

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	@Transactional
	public void testGetExistingId(){
		
		BookEO book = TestUtils.generateBook("My New Book");
		bookDAO.save(book); 
		
		BookEO bookFound = bookDAO.get(book.getId());
		assertNotNull(bookFound);
	}
	
	@Test
    @Transactional
    public void testGetExistingIdCriteria(){
        
        BookEO book = TestUtils.generateBook("My New Book");
        bookDAO.save(book); 
        
        
        final Long bookId = book.getId();
        
        BookEO bookFound = bookDAO.find(new CB(){{
            idEq(bookId);
        }});
        assertNotNull(bookFound);
    }
	
	@Test
	@Transactional
	public void testGetNonExistingId(){
		
		BookEO book = bookDAO.get(1000L);
		assertNull(book);
	}
	
	@Test(expected=IllegalArgumentException.class)
	@Transactional
	public void testGetNullId(){
		BookEO book = bookDAO.get(null);
		
		assertNull(book);
	}
	
	@Test
	@Transactional
	public void testExistsWithExistingId(){
		
		BookEO book = TestUtils.generateBook("My New Book");
		bookDAO.save(book); 
		
		boolean exists = bookDAO.exists(book.getId());
		
		assertTrue(exists);
	}
	
	@Test
	@Transactional
	public void testExistsWithNonExistingId(){
		
		boolean exists = bookDAO.exists(1000L);
		assertFalse(exists);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	@Transactional
	public void testExistsWithNullId(){
		
		boolean exists = bookDAO.exists(null);
		assertFalse(exists);
		
	}
	
	@Test
	@Transactional
	public void testFindAll(){
		
		TestUtils.generateAndSaveBooks("Some Book", 20, bookDAO);
		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll();
		
		int totalCount = bookResultSet.count().intValue();
		int listSize   = bookResultSet.list().size();
		
		
		assertSame(totalCount,listSize);
		
	}
	
	@Test
	@Transactional
	public void testFindAllPaginated(){
			
		TestUtils.generateAndSaveBooks("Some Book", 20, bookDAO);
		
		final int PAGE_SIZE = 10;

		DResultSet<BookEO> bookResultSet = bookDAO.findAll(PAGE_SIZE,0);
		
		int totalCount = bookResultSet.count().intValue();
		int listSize   = bookResultSet.list().size();
		
		boolean listSizeSameAsPageSize = listSize == PAGE_SIZE;
		boolean totalCountNotEqualToListSize = listSize != totalCount;
		
		assertTrue(listSizeSameAsPageSize && totalCountNotEqualToListSize);
		
	}
	
	@Test
	@Transactional
	public void testFindAllHQLWithNoParams(){
			
		TestUtils.generateAndSaveBooks("Some Book", 20, bookDAO);
		
		final String HQL_QUERY = "from BookEO where 1=1";

		List<BookEO> bookList = bookDAO.findAll(HQL_QUERY);
		

		assertTrue(bookList.size()>0);
		
	}
	
	@Test
	@Transactional
	public void testFindAllHQLWithParams(){
			
		TestUtils.generateAndSaveBooks("Some Book", 20, bookDAO);
		
		final String HQL_QUERY = "from BookEO where title like :search";
		
		final String SEARCH = "Book%";
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("search", SEARCH);

		List<BookEO> bookList = bookDAO.findAll(HQL_QUERY,params);
		
		assertTrue(bookList.size()==0);
		
	}
	
	@Test
	@Transactional
	public void testFindOneHQLWithTitleParam(){
			
		final String BOOK_TITLE = "MY CRITERIA BUILDER FOR DUMMIES";
		final String DONT_SEARCH_ME = "DONT SEARCH ME";
		
		TestUtils.generateAndSaveBook(BOOK_TITLE, bookDAO);
		TestUtils.generateAndSaveBook(DONT_SEARCH_ME, bookDAO);
		
		final String HQL_QUERY = "from BookEO where title=:title";
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("title", BOOK_TITLE);

		BookEO book = bookDAO.find(HQL_QUERY,params);
		
		assertNotNull(book);
		
	}
	
	@Test
	@Transactional
	public void testCount(){
		
		int NUMBER_OF_BOOKS_TO_INSERT = 5;
			
		TestUtils.generateAndSaveBooks("Some Book",NUMBER_OF_BOOKS_TO_INSERT, bookDAO);
		
		Long bookCount = bookDAO.count();
				
		log.info("BOOK COUNT IS "+bookCount);
		
		assertTrue(bookCount == 105);
		
	}
	
	@Test
	@Transactional
	public void testCountByTitleEqualsSomeBook(){
		
		final String BOOK_TITLE = "MY CRITERIA BUILDER FOR DUMMIES";
		final String DONT_SEARCH_ME = "DONT SEARCH ME";
		
		TestUtils.generateAndSaveBook(BOOK_TITLE, bookDAO);
		TestUtils.generateAndSaveBook(DONT_SEARCH_ME, bookDAO);
		
		Long bookCount = bookDAO.count(new FB(){{
			eq("title",BOOK_TITLE);
		}});
				
		log.info("BOOK COUNT IS "+bookCount);
		
		assertTrue(bookCount == 1);
		
	}
	
	@Test
	@Transactional
	public void testCountByNonExistentTitle(){
		
		final String BOOK_TITLE = "MY CRITERIA BUILDER FOR DUMMIES";
		final String DONT_SEARCH_ME = "DONT SEARCH ME";
		final String I_DONT_EXIST = "I DONT EXIST";
		
		TestUtils.generateAndSaveBook(BOOK_TITLE, bookDAO);
		TestUtils.generateAndSaveBook(DONT_SEARCH_ME, bookDAO);
		
		Long bookCount = bookDAO.count(new FB(){{
			eq("title",I_DONT_EXIST);
		}});
				
		log.info("BOOK COUNT IS "+bookCount);
		
		assertTrue(bookCount == 0);
		
	}
	
	@Test
	@Transactional
	public void testCountByNullTitle(){
		
		final String BOOK_TITLE = "MY CRITERIA BUILDER FOR DUMMIES";
		final String DONT_SEARCH_ME = "DONT SEARCH ME";
		
		TestUtils.generateAndSaveBook(BOOK_TITLE, bookDAO);
		TestUtils.generateAndSaveBook(DONT_SEARCH_ME, bookDAO);
		
		Long bookCount = bookDAO.count(new FB(){{
			eq("title",null);
		}});
		

		log.info("BOOK COUNT IS "+bookCount);
		
		assertTrue(bookCount == 0);
		
	}
	
	
	@Test
	@Transactional
	public void testFindOne(){
		
		int NUMBER_OF_BOOKS_TO_INSERT = 5;
			
		TestUtils.generateAndSaveBooks("Some Book",NUMBER_OF_BOOKS_TO_INSERT, bookDAO);
		
		BookEO someBook = bookDAO.find();

		assertNotNull(someBook);
		
	}
	
	@Test
	@Transactional
	public void testFindByTitleEqualsSomeBook(){
		
		final String BOOK_TITLE = "MY CRITERIA BUILDER FOR DUMMIES";
		final String DONT_SEARCH_ME = "DONT SEARCH ME";
		
		TestUtils.generateAndSaveBook(BOOK_TITLE, bookDAO);
		TestUtils.generateAndSaveBook(DONT_SEARCH_ME, bookDAO);
		
		BookEO someBook = bookDAO.find(new CB(){{
			eq("title",BOOK_TITLE);
		}});
				

		assertNotNull(someBook);
		
	}
	
	@Test
	@Transactional
	public void testFindByNonExistentTitle(){
		
		final String BOOK_TITLE = "MY CRITERIA BUILDER FOR DUMMIES";
		final String DONT_SEARCH_ME = "DONT SEARCH ME";
		final String I_DONT_EXIST = "I DONT EXIST";
		
		TestUtils.generateAndSaveBook(BOOK_TITLE, bookDAO);
		TestUtils.generateAndSaveBook(DONT_SEARCH_ME, bookDAO);
		
		BookEO someBook = bookDAO.find(new CB(){{
			eq("title",I_DONT_EXIST);
		}});
				

		assertNull(someBook);
		
	}
	
	@Test
	@Transactional
	public void testFindByNullTitle(){
		
		final String BOOK_TITLE = "MY CRITERIA BUILDER FOR DUMMIES";
		final String DONT_SEARCH_ME = "DONT SEARCH ME";
		
		TestUtils.generateAndSaveBook(BOOK_TITLE, bookDAO);
		TestUtils.generateAndSaveBook(DONT_SEARCH_ME, bookDAO);
		
		BookEO someBook = bookDAO.find(new CB(){{
			eq("title",null);
		}});
				
		
		assertNull(someBook);
		
	}
	
	@Test
	@Transactional
	public void testFindAllByUnitsBoughtAreGreaterThanUnitsSold(){
		
		
		BookEO book1 = TestUtils.generateBook("Where units bought are greater");
		book1.setUnitsBought(10000);
		book1.setUnitsSold(400);
		
		BookEO book2 = TestUtils.generateBook("Where units bought are greater chap2");
		book2.setUnitsBought(999);
		book2.setUnitsSold(998);
		
		BookEO book3 = TestUtils.generateBook("Where units bought are lower");
		book3.setUnitsBought(999);
		book3.setUnitsSold(1500);
		
		bookDAO.save(book1);
		bookDAO.save(book2);
		bookDAO.save(book3);
		
		DResultSet<BookEO> bookResultSet =
			bookDAO.findAll(new CB(){{
			
			gtProperty("unitsBought", "unitsSold");
			
		}});
				
		assertTrue(bookResultSet.count() == 55);
		
	}
	
	@Test
	@Transactional
	public void testFindAllByUnitsBoughtAreGreaterThan2222OrTitleEqualsDummy(){
		
		
		BookEO book1 = TestUtils.generateBook("Where units bought are greater");
		book1.setUnitsBought(10000);
		
		BookEO book2 = TestUtils.generateBook("Where units bought are greater chap2");
		book2.setUnitsBought(999);
		
		BookEO book3 = TestUtils.generateBook("Where units bought are lower");
		book3.setUnitsBought(2223);
		
		final String DUMMY_TITLE = "Dummy";
		
		BookEO book4 = TestUtils.generateBook(DUMMY_TITLE);
		book4.setUnitsBought(80);
		
		bookDAO.save(book1); //SHOULD BE FOUND
		bookDAO.save(book2); //SHOULD NOT BE FOUND
		bookDAO.save(book3); //SHOULD BE FOUND
		bookDAO.save(book4); //SHOULD BE FOUND
		
		final int UNITS_BOUGHT = 2222;
		
		DResultSet<BookEO> bookResultSet =
			bookDAO.findAll(new CB(){{
				
				or(new FB(){{
					
					gt("unitsBought",UNITS_BOUGHT);
					eq("title",DUMMY_TITLE);
					
				}});

		}});
		
		
		assertTrue(bookResultSet.count() == 96);
		
	}
	
	
	@Test
	@Transactional
	public void testUpdateEntityObject(){
		
		BookEO book1 = TestUtils.generateBook("ORIGINAL TITLE");
		book1.setUnitsBought(10000);
		bookDAO.save(book1); //SHOULD BE FOUND		
		
		BookEO edited = bookDAO.get(book1.getId());
		edited.setTitle("MODIFIED TITLE");
		bookDAO.save(edited);
	
		BookEO retrieved = bookDAO.get(edited.getId());
		
		assertSame("MODIFIED TITLE", retrieved.getTitle());
		
	}
	
	@Test
	@Transactional
	public void testSearchAuthorWithBookCriteria(){
		
		TestUtils.generateAndSaveBooksWithAutor("JPAUtils", 30, bookDAO, authorDAO);

		DResultSet<AuthorEO> autorRS = authorDAO.findAll(new CB(){{
			
			
			join("books",new FB(){{
				
				like("title","%3%");
				
			}});
			
		}});

		
		assertTrue(autorRS.count() > 0);
		
	}
	
	
	@Test
    @Transactional
    public void testSearchAuthorWithBookReleaseDateCriteria(){
        
        TestUtils.generateAndSaveBooksWithAutor("JPAUtils", 30, bookDAO, authorDAO);

        DResultSet<AuthorEO> autorRS = authorDAO.findAll(new CB(){{
            

            join("books",new FB(){{
                
                gt("releaseDate",new Date());
                
            }});
            
        }});

        assertFalse(autorRS.list().isEmpty());
        assertTrue(autorRS.count() > 0);
        
    }
	
	@Test
    @Transactional
    public void testFindAllBooksUnitsSoldLT(){
	    
	    final int UNITS_SOLD = 100;
        
        TestUtils.generateAndSaveBooksWithAutor("JPAUtils", 30, bookDAO, authorDAO);

        DResultSet<BookEO> bookRS = bookDAO.findAll(new CB(){{
            
              lt("unitsSold",UNITS_SOLD);
            
        }});

        assertFalse(bookRS.list().isEmpty());
        assertTrue(bookRS.count() > 0);
        
    }
	
	@Test
    @Transactional
    public void testFindAllBooksUnitsSoldLE(){
        
        final int UNITS_SOLD = 100;
        
        TestUtils.generateAndSaveBooksWithAutor("JPAUtils", 30, bookDAO, authorDAO);

        DResultSet<BookEO> bookRS = bookDAO.findAll(new CB(){{
            
              le("unitsSold",UNITS_SOLD);
            
        }});

        assertFalse(bookRS.list().isEmpty());
        assertTrue(bookRS.count() > 0);
        
    }
	
	@Test
    @Transactional
    public void testFindAllBooksUnitsSoldGT(){
        
        final int UNITS_SOLD = 100;
        
        TestUtils.generateAndSaveBooksWithAutor("JPAUtils", 30, bookDAO, authorDAO);

        DResultSet<BookEO> bookRS = bookDAO.findAll(new CB(){{
            
              gt("unitsSold",UNITS_SOLD);
            
        }});

        assertFalse(bookRS.list().isEmpty());
        assertTrue(bookRS.count() > 0);
        
    }
	
	@Test
    @Transactional
    public void testFindAllBooksUnitsSoldGE(){
        
        final int UNITS_SOLD = 100;
        
        TestUtils.generateAndSaveBooksWithAutor("JPAUtils", 30, bookDAO, authorDAO);

        DResultSet<BookEO> bookRS = bookDAO.findAll(new CB(){{
            
              ge("unitsSold",UNITS_SOLD);
            
        }});

        assertFalse(bookRS.list().isEmpty());
        assertTrue(bookRS.count() > 0);
        
    }

}
