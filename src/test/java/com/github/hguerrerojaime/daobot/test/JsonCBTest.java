package com.github.hguerrerojaime.daobot.test;


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
import com.github.hguerrerojaime.daobot.core.ResultSet;
import com.github.hguerrerojaime.daobot.core.JsonCB;
import com.github.hguerrerojaime.daobot.dao.GenericDAO;
import com.github.hguerrerojaime.daobot.test.eo.BookEO;
import com.github.hguerrerojaime.daobot.test.utils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "/META-INF/applicationContext.xml" 
})
@Transactional
public class JsonCBTest {
    
    @Autowired
    GenericDAO genericDAO;

    @Test
    public void testJQL() throws JsonParseException, JsonMappingException, IOException {
        
        String jsonQuery = TestUtils.loadJsonQuery("bookQuery.json");
        
        ResultSet<BookEO> bookRS = genericDAO.findAll(BookEO.class, new JsonCB(jsonQuery));
        
        assertTrue(bookRS.count() > 0);
    }
    
    @Test
    public void testSearchAuthorWithBookCriteria() throws JsonParseException, JsonMappingException, IOException{
        
  
        String jsonQuery = TestUtils.loadJsonQuery("bookJoinQuery.json");
        
        ResultSet<BookEO> bookRS = genericDAO.findAll(BookEO.class, new JsonCB(jsonQuery));
        
        assertTrue(bookRS.count() > 0);

    }
    

}
