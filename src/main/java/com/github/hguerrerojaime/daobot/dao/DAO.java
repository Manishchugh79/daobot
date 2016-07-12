package com.github.hguerrerojaime.daobot.dao;

import java.io.Serializable;

import com.github.hguerrerojaime.daobot.core.AbstractCB;
import com.github.hguerrerojaime.daobot.core.CB;
import com.github.hguerrerojaime.daobot.core.DynamicQueryBuilder;
import com.github.hguerrerojaime.daobot.core.ResultSet;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * @author Humberto Guerrero Jaime
 *
 * @param <T> the EntityObject type
 * @param <K> the Primary Key for the Entity Object
 */
 public interface DAO<T extends EntityObject<K>,K extends Serializable> extends GenericDAO{
	
	

	/* Read only methods */
	
	/**
	 * Fetches a record with the given id
	 * 
	 * @param id
	 * @return The Instance
	 */
	 T get(K id);
	
	
	/**
	 * Same as get(id) != null;
	 * Checks if a record exists with the given id
	 * 
	 * @param id
	 * @return True if instance is not null, False otherwise
	 */
	 boolean exists(K id);
	
	

	/**
	 * Same as findBy(new CB());
	 * Fetches the first record it finds, not very useful
	 * 
	 * @return The Instance
	 */
	 T find();
	
	/**
	 * Finds the first record matching the given criteria
	 * 
	 * @param criteria - The criteria to be met (Filters, Orders)
	 * @return The Instance
	 */
	 T find(CB criteria);
	
	
	/**
	 * Same as findAll(0,0);
	 * Fetches all the records
	 * 
	 * @return JPAResulset containing the fetched records and the total record count
	 */
	 ResultSet<T> findAll();
	
	
	/**
	 * Same as findAll(new JPAFilterBuilder(),max,offset);
	 * Fetches all the records paginating the results
	 * 
	 * @param max - The maximum number of records to be fetched
	 * @param offset - The first record position to be fetched
	 * @return JPAResulset containing the fetched records and the total record count
	 */
	 ResultSet<T> findAll(int max,int offset);
	
	
	/**
	 * Same as findAllBy(criteria,0,0);
	 * Fetches ALL the records matching the given criteria
	 * 
	 * @param criteria - The criteria to be met (Filters, Orders)
	 * @return JPAResulset containing the fetched records and the total record count
	 */
	 ResultSet<T> findAll(AbstractCB criteria);
	
	
	/**
	 * Fetches all the records matching the given criteria
	 * paginating the results
	 * 
	 * @param criteria - The criteria to be met (Filters, Orders)
	 * @param max - The maximum number of records to be fetched
	 * @param offset - The first record position to be fetched
	 * @return JPAResulset containing the fetched records and the total record count
	 */
	 ResultSet<T> findAll(AbstractCB criteria,int max,int offset);
	

	/**
	 * Same as countBy(new JPAFilterBuilder());
	 * Fetches the count of all the records
	 * @return the record count
	 */
	 Long count();
	
	
	/**
	 * Fetches the count of all the records matching the given criteria
	 * 
	 * @param filterBuilder - The criteria to be met
	 * @return
	 */
	 Long count(AbstractCB filterBuilder);
	 
	 /**
	 * @return
	 */
	DynamicQueryBuilder<T, K> fromEntity();

}
