package com.hgj.daobot.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hgj.daobot.core.CB;
import com.hgj.daobot.core.DOrder;
import com.hgj.daobot.core.DResultSet;
import com.hgj.daobot.core.FB;
import com.hgj.daobot.test.dao.AuthorDAO;
import com.hgj.daobot.test.dao.BookDAO;
import com.hgj.daobot.test.eo.AuthorEO;
import com.hgj.daobot.test.eo.BookEO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/META-INF/applicationContext.xml" 
})
public class CBvsHQLTest {
	
	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private AuthorDAO authorDAO;
	
	@Transactional
	@Test
	public void testSingleDinamicFieldSearchWithHQL(){
		
		final String dynamicField = "title";
		final String hql = "from BookEO where "+dynamicField+" like ?";
		final String query= "mon%";
		
		List<BookEO> bookList = bookDAO.findAll(hql,new Object[]{ query });
		
		assertTrue(!bookList.isEmpty());
		
	}
	
	@Transactional
	@Test
	public void testSingleDinamicFieldSearchWithCB(){
		
		final String dynamicField = "title";
		final String query= "mon%";
		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			 like(dynamicField,query);
		}});
		
		
		assertTrue(!bookResultSet.list().isEmpty());
		
	}
	
	@Transactional
	@Test
	public void testMultipleDinamicFieldSearchWithHQL(){
		

		final String[] dynamicFields = {"title","description","unitsSold","unitsBought"};

		final Object[] values = {"JPAUtils 12","some description",1000,1000};
		
		StringBuilder hql = new StringBuilder("from BookEO where 1=1 ");
		
		for(String dinamicField : dynamicFields){
			hql.append("and ")
			   .append(dinamicField)
			   .append("=?");
		}
		
		
		List<BookEO> bookList = bookDAO.findAll(hql.toString(),values);
		
		
		assertTrue(bookList.size()>=0);
		
	}
	
	@Transactional
	@Test
	public void testMultipleDinamicFieldSearchWithCB(){
		
		final String[] dynamicFields = {"title","description","unitsSold","unitsBought"};

		final Object[] values = {"JPAUtils 12","some description",1000,1000};
		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			
			for (int i = 0; i < dynamicFields.length; i++) {
				eq(dynamicFields[i],values[i]);
			}
			
		}});
		
		
		assertTrue(bookResultSet.list().size()>=0);
		
	}
	
	@Test
	@Transactional
	public void testSearchAuthorWithBookDinamicFieldHQL(){
		
		final String dynamicField = "title";
		final String query= "JPAUtils 1";
		
		StringBuilder hqlAlt = new StringBuilder();
		
		hqlAlt.append("from AuthorEO eo inner join eo.books b where b.author.id=id ")
		   .append("and b.")
		   .append(dynamicField)
		   .append(" like ?");
		   
		
		
		List<AuthorEO> authorList = authorDAO.findAll(hqlAlt.toString(),new Object[]{query});
		
		assertTrue(authorList.size()>=0);
		
	}

	
	@Test
	@Transactional
	public void testSearchAuthorWithBookDinamicFieldCB(){
		
		final String dynamicField = "title";
		final String query= "mon%";
		
		DResultSet<AuthorEO> autorRS = authorDAO.findAll(new CB(){{
			
			join("books",new FB(){{
				like(dynamicField,query);
			}});

		}});
		
		assertTrue(autorRS.list().size()>=0);
		
	}
	
	@Test
	@Transactional
	public void testSortMultipleFiledsHQL(){
		
		final String[] dynamicFields = {"title","description","unitsSold","unitsBought"};
	
		
		StringBuilder hql = new StringBuilder();
		
		hql.append("from BookEO order by ");
		
		boolean firstRun = true;
		
		for(String field: dynamicFields){
			
			if(firstRun){
				firstRun = false;
			}else{
				hql.append(", ");
			}
			
			hql.append(field);
			  
			
		}
		
		System.out.println(hql.toString());
		
		List<BookEO> bookList = bookDAO.findAll(hql.toString(),10,0);
		
		assertTrue(bookList.size()>0);
		
	}
	
	@Test
	@Transactional
	public void testSortMultipleFiledsCB(){
		
		final String[] dynamicFields = {"title","description","unitsSold","unitsBought"};
	
		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			
			for(String field : dynamicFields){
				sort(field,DOrder.DESC);
			}
			
		}},10,0);
		

		assertTrue(bookResultSet.list().size() > 1);
		
	}
	
	@Test
	@Transactional
	public void testCustomQuery(){
		

		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			
			join("author",new FB(){{
				ilike("name","acton%");
			}});
			
			geProperty("unitsSold", "unitsBought");
			
			sort("unitsSold");
			
		}},10,0);

		assertTrue(bookResultSet.get() != null);
		assertTrue(!bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() > 0);
		
	}
	
	@Test
	@Transactional
	public void testCustomQuery2(){
		

		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			
			join("author",new FB(){{
				ilike("name","acton%");
			}});
			
			geProperty("unitsSold", "unitsBought");
			
			
			
		}},1,0);

		assertTrue(bookResultSet.get() != null);
		assertTrue(!bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() > 0);
		
	}
	
	@Test
	@Transactional
	public void testCustomQuery3(){
		

		
		DResultSet<BookEO> bookResultSet = bookDAO.findAll(new CB(){{
			
			join("author",new FB(){{
				ilike("name","actonaaaaaaaaa%");
			}});
			
		}},1,0);

		assertTrue(bookResultSet.get() == null);
		assertTrue(bookResultSet.list().isEmpty());
		assertTrue(bookResultSet.count() == 0);
		
	}

}
