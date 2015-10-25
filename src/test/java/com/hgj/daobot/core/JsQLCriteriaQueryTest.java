package com.hgj.daobot.core;


import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hgj.daobot.dao.GenericDAO;
import com.hgj.daobot.test.eo.BookEO;
import com.hgj.daobot.test.utils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "/META-INF/applicationContext.xml" 
})
@Transactional
public class JsQLCriteriaQueryTest {
    
    @Autowired
    GenericDAO genericDAO;

    @Test
    public void testJQL() throws JsonParseException, JsonMappingException, IOException {
        
        String jsonQuery = TestUtils.loadJsonQuery("bookQuery.json");
        
        DResultSet<BookEO> bookRS = genericDAO.findAll(BookEO.class, new JsQLCriteriaQuery(jsonQuery));
        
        assertTrue(bookRS.count() > 0);
    }
    
    @Test
    public void testSearchAuthorWithBookCriteria() throws JsonParseException, JsonMappingException, IOException{
        
  
        String jsonQuery = TestUtils.loadJsonQuery("bookJoinQuery.json");
        
        DResultSet<BookEO> bookRS = genericDAO.findAll(BookEO.class, new JsQLCriteriaQuery(jsonQuery));
        
        assertTrue(bookRS.count() > 0);

    }
    
    /*
    @Test
    public void test() throws JsonParseException, JsonMappingException, IOException {
        

        DResultSet<BookEO> bookRS = genericDAO.findAll(BookEO.class, new DCB(){{
            ilike("title","ornare%");
        }});
        
        System.out.println(bookRS.count());
        
    }
    */

}
