package com.github.hguerrerojaime.daobot.utils;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Id;

import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * General utils for the JPAUtils API
 * 
 * @author Humberto Guerrero Jaime
 *
 */
public class Utils {
	
	public static <T extends EntityObject<PK>,PK extends Serializable> Field getIdField(Class<T> entityClass){
		
		Field idField = null;
		
		for(Field field: entityClass.getDeclaredFields()){
			
			if(field.getAnnotation(Id.class) != null){
				idField = field;
				break;
			}
			
		}
		
		return idField;
	}
	
	public static String replaceWholeWord(String text,String regex,String replacement){
		
		StringBuilder wrapper = new StringBuilder();
		
		wrapper.append("\\b")
			.append(regex)
			.append("\\b");

	    return text.replaceFirst(wrapper.toString(), replacement);
	}

}
