package com.github.hguerrerojaime.daobot.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

    private JsonUtils() {
    }

    public static String encode(Object object) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    
    public static JsonNode decode(String json) throws JsonParseException, JsonMappingException, IOException{
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	return mapper.readTree(json);
    	
    }
    

}