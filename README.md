# DAOBOT
- Java DAO (Data Access Object) Generics.

- Database Queries made easy to read and to maintain.

- Can transform JSON Objects into queries for Back-End/Front-End communication.

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