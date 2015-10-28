package com.github.hguerrerojaime.daobot.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.hguerrerojaime.daobot.exceptions.JsonQueryException;
import com.github.hguerrerojaime.daobot.utils.JsonUtils;
import com.github.hguerrerojaime.daobot.utils.Messages;

public class JsQLCriteriaQuery extends JsQLFilterQuery {
    
    private List<QuerySort> sortList;
    
    public JsQLCriteriaQuery(String json)
            throws JsonParseException, JsonMappingException, IOException {
        this(JsonUtils.decode(json));
    }

    public JsQLCriteriaQuery(JsonNode rootNode) {
        super(rootNode);
        sortList = new ArrayList<QuerySort>();
    }
    
    @Override
    public FilterGroup build() {
        buildSort();
        return super.build();
    }
    
    private void buildSort(){
        
        final String code = "sort";
        
        JsonNode rootNode = getRootNode();
        
        Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields();

        while (it.hasNext()) {

            Map.Entry<String, JsonNode> element = it.next();

            String nodeKey = element.getKey();
            JsonNode nodeValue = element.getValue();
            
            if(nodeKey.equals(code)){
                
                
                Iterator<JsonNode> sortIterator = nodeValue.iterator();
                
                while(sortIterator.hasNext()){
                    
                    JsonNode sortElement = sortIterator.next();
                    
                    boolean hasSingleArg = sortElement.size() == 1;
                    boolean hasTwoArgs = sortElement.size() == 2;
                    
                    if(!hasSingleArg && !hasTwoArgs){
                        throw new JsonQueryException(
                            Messages.read("jsonquery.exception.invalid.noargs",new Object[]{ code,"1 or 2",nodeValue.size() })
                        );
                    }
                    
                
                    String sortField = sortElement.get(0).asText();
                    DOrder order = getOrder(hasTwoArgs ? sortElement.get(1).asText() : null);
                    
                    getSortList().add(new QuerySort(sortField, order));
                
                }
            }
        
        }
            
    }
    
    private DOrder getOrder(String orderCode){
        

        if(orderCode == null){
            return DOrder.ASC;
        }
        
        if(!orderCode.equals("asc") && !orderCode.equals("desc")){
            throw new JsonQueryException("Invalid order code, must be asc or desc");
        }
        
        return DOrder.valueOf(orderCode.toUpperCase());
        
    }
    
    public List<QuerySort> getSortList() {
        return sortList;
    }

}
