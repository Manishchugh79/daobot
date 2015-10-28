# DAOBOT
- Java DAO (Data Access Object) Generics.

- Database Queries made easy to read and to maintain.

- Can transform JSON Objects into queries for Back-End/Front-End communication.

----------

# Basic Example Usage

Find all Books where "title" like "mon%"

	@Transactional
	@Test
	public void testSingleDinamicFieldSearchWithCB(){
		
		final String dynamicField = "title";
		final String query= "mon%";
			
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			 like(dynamicField,query);
		}});
			
			
		assertFalse(bookResultSet.list().isEmpty());
			
	}