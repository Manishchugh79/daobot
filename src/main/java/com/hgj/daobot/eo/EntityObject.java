package com.hgj.daobot.eo;

import java.io.Serializable;

/**
 * EO refers to Entity Object, its any object that can be persisted into
 * the database
 * 
 * @author Humberto Guerrero Jaime
 *
 * @param <K> the Primary Key for the Entity Object
 */
public interface EntityObject<K extends Serializable> extends Serializable{
	
	/**
	 * @return the Primary key for the Entity Object
	 */
	public K getId();

}
