package com.github.hguerrerojaime.daobot.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.JoinType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.hguerrerojaime.daobot.core.ConditionFilter.Type;
import com.github.hguerrerojaime.daobot.exceptions.JsonQueryException;
import com.github.hguerrerojaime.daobot.utils.JsonUtils;
import com.github.hguerrerojaime.daobot.utils.Messages;

public class JsQLFilterQuery extends AbstracQueryBuilder {

    private JsonNode rootNode;
    private FilterGroup filterBuild;

    public JsQLFilterQuery(String json)
            throws JsonParseException, JsonMappingException, IOException {
        this(JsonUtils.decode(json));
    }

    public JsQLFilterQuery(JsonNode rootNode) {
        super();

        this.rootNode = rootNode;

        if (!rootNode.isObject()) {
            throw new JsonQueryException(
                    "The root node must be an object type");
        }
    }
    
    

    protected JsonNode getRootNode() {
        return rootNode;
    }

    @Override
    public FilterGroup build() {
        getFilters().clear();
        buildGroups();
        buildFilters();
        
        filterBuild = new FilterGroup(getFilterGroupType());
        filterBuild.getFilters().addAll(getFilters());
        return filterBuild;
    }
    
    private void buildGroups() {
        
        final String AND = "and";
        final String OR = "or";
        final String NOT = "not";

        Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields();
        
        final List<String> groupKeys = Arrays.asList(new String[]{ AND,OR,NOT });

        while (it.hasNext()) {

            Map.Entry<String, JsonNode> element = it.next();

            String nodeKey = element.getKey();
            JsonNode nodeValue = element.getValue();

            if(groupKeys.contains(nodeKey)){
                
                if(nodeKey.equals(AND)){
         
                    buildTypeGroup("and",FilterGroup.Type.AND,nodeValue);
                    
                }else if(nodeKey.equals(OR)){
         
                    buildTypeGroup("or",FilterGroup.Type.OR,nodeValue);
                    
                }else if(nodeKey.equals(NOT)){
         
                    buildTypeGroup("not",FilterGroup.Type.NAND,nodeValue);
                    
                }
                
            }

        }

    }
    
    private void buildTypeGroup(String code,FilterGroup.Type groupType,JsonNode body){
        
        final String correctFormat = "[ {filters} ]";
        
        if (!body.isArray()) {
            throw new JsonQueryException(
                Messages.read("jsonquery.exception.invalid.format",new Object[]{ code,correctFormat })
            );
        }
        
        Iterator<JsonNode> it = body.iterator();

        while (it.hasNext()) {
            
            JsonNode element = it.next();
            
            if(!element.isObject()){
                throw new JsonQueryException(
                    Messages.read("jsonquery.exception.invalid.format",new Object[]{ code,correctFormat })
                );
            }
            
            FilterGroup childGroup = new JsQLFilterQuery(element).build();
            
            childGroup.setGroupType(groupType);
            
            addQueryFilter(childGroup);
            
        }
        
    }
    
    
    
    private void buildFilters() {

        Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields();

        while (it.hasNext()) {

            Map.Entry<String, JsonNode> element = it.next();

            String nodeKey = element.getKey();
            JsonNode nodeValue = element.getValue();

            ConditionFilter.Type type = ConditionFilter.Type
                    .byCode(nodeKey);

            if (type != null) {
                
                if(type.equals(ConditionFilter.Type.JOIN)){
                    
                    buildJoinFilter(nodeValue, type);
                }else{
                
                    buildTypeFilter(nodeValue,type);
                    
                }
            }

        }

    }
    
    

    private void buildTypeFilter(JsonNode body,ConditionFilter.Type filterType) {
        
        final String correctFormat = "[ [args] ]";

        if (!body.isArray()) {
            throw new JsonQueryException(
                Messages.read("jsonquery.exception.invalid.format",new Object[]{ filterType.getCode(),correctFormat })
            );
        }

        Iterator<JsonNode> it = body.iterator();

        while (it.hasNext()) {

            JsonNode element = it.next();

            if (!element.isArray()) {
                throw new JsonQueryException(
                    Messages.read("jsonquery.exception.invalid.format",new Object[]{ filterType.getCode(),correctFormat })
                );
            }

            if (element.size() != filterType.getNumArgs()) {
                throw new JsonQueryException(
                    Messages.read("jsonquery.exception.invalid.noargs",new Object[]{ filterType.getCode(),filterType.getNumArgs(),element.size() })
                );
            }

            List<Object> args = new ArrayList<Object>();
            
            for (int i = 0; i < element.size(); i++) {
                args.add(getNativeObject(element.get(i)));
            }
            

            addQueryFilter(new ConditionFilter(filterType, args.toArray(new Object[0])));

        }
        
    }
    
    private void buildJoinFilter(JsonNode body,ConditionFilter.Type filterType){
        
        final String correctFormat = "[ [args] ]";
        
        if (!body.isArray()) {
            throw new JsonQueryException(
                Messages.read("jsonquery.exception.invalid.format",new Object[]{ filterType.getCode(),correctFormat })
            );
        }
        
        Iterator<JsonNode> it = body.iterator();

        while (it.hasNext()) {

            JsonNode element = it.next();
            
            if (!element.isArray()) {
                throw new JsonQueryException(
                    Messages.read("jsonquery.exception.invalid.format",new Object[]{ filterType.getCode(),correctFormat })
                );
            }
            
            boolean hasTwoArgs = element.size() == 2;
            boolean hasThreeArgs = element.size() == 3;
            
            if(element.size() != 2 && element.size() != 3 ){
                throw new JsonQueryException(
                    Messages.read("jsonquery.exception.invalid.noargs",new Object[]{ filterType.getCode(),"2 or 3",element.size() })
                );
            }
            
            if(hasTwoArgs && !element.get(1).isObject()){
                throw new JsonQueryException(
                    Messages.read("jsonquery.exception.invalid.argtype",new Object[]{ "Object" })
                );
            }
            
            if(hasThreeArgs && !element.get(2).isObject()){
                throw new JsonQueryException(
                    Messages.read("jsonquery.exception.invalid.argtype",new Object[]{ "Object" })
                );
            }
            
            String fieldName = element.get(0).asText();
            JoinType joinType = getJoinType(hasThreeArgs ? element.get(1).asText() : null);
            JsonNode joinBody = hasThreeArgs ? element.get(2) : element.get(1);
            JsQLFilterQuery joinFilterBuilder = new JsQLFilterQuery(joinBody);
            
            addQueryFilter(new ConditionFilter(Type.JOIN, new Object[]{ fieldName,joinFilterBuilder,joinType }));
            
        }
        
        
    }
    
    private JoinType getJoinType(String key){
        
        if(key == null){
            return JoinType.INNER;
        }
        
        List<String> joinTypes = Arrays.asList(new String[]{"inner","right","left"});
        
        if(!joinTypes.contains(key)){
            
            throw new JsonQueryException("Invalid join type");
            
        }
        
        return JoinType.valueOf(key.toUpperCase());
    }
    
    private Object getNativeObject(JsonNode element){
        
        if(element.isTextual()){
            return element.asText();
        }else if(element.isDouble()){
            return element.asDouble();
        }else if(element.isInt()){
            return element.asInt();
        }else if(element.isDouble()){
            return element.asDouble();
        }else if(element.isLong()){
            return element.asLong();
        }else if(element.isBoolean()){
            return element.asBoolean();
        }
        
        return null;
    }


}
