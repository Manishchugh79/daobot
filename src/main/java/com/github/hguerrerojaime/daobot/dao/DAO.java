package com.github.hguerrerojaime.daobot.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.github.hguerrerojaime.daobot.core.AbstractCB;
import com.github.hguerrerojaime.daobot.core.CB;
import com.github.hguerrerojaime.daobot.core.ResultSet;
import com.github.hguerrerojaime.daobot.core.JsonCB;
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
	 * Same as findBy(new JPACriteriaBuilder());
	 * Fetches the first record it finds, not very useful
	 * 
	 * @return The Instance
	 */
	 T find();
	
	
	/**
	 * Same as findOne(hql,new HashMap<String,Object>());
	 * Fetches the fist result that matches the given query
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findOne("from Book where 1=1");
	 * @return
	 */
	 T find(String hql);
	
	/**
	 * Fetches the fist result that matches the given query
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findOne("from Book where 1=1");
	 * @param params the parameter map
	 * @return
	 */
	 T find(String hql,Map<String,Object> params);
	
	/**
	 * Finds the first record matching the given criteria
	 * 
	 * @param criteriaBuilder - The criteria to be met (Filters, Orders)
	 * @return The Instance
	 */
	 T find(CB criteriaBuilder);
	
	
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
	 * Same as findAll(hql,0,0);
	 * Fetches all the results and the result count that matches the
	 * given hql query
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @return the resultset
	 */
	 List<T> findAll(String hql);
	

	/**
	 * Same as findAll(hql,new HashMap<String,Object>(),max,offset);
	 * Fetches all the results and the result count that matches the
	 * given hql query paginating the results
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @param max
	 * @param offset
	 * @return the resultset
	 */
	 List<T> findAll(String hql,int max,int offset);
	
	/**
	 * Same as findAll(hql,params,0,0);
	 * Fetches all the results and the result count that matches the
	 * given hql query
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @param params the parameter map
	 * @return the resultset
	 */
	 List<T> findAll(String hql,Map<String,Object> params);
	
	/**
	 * Same as findAll(hql,params,0,0);
	 * Fetches all the results and the result count that matches the
	 * given hql query
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @param params the parameter list
	 * @return the resultset
	 */
	 List<T> findAll(String hql,Object[] params);
	

	/**
	 * Fetches all the results and the result count that matches the
	 * given hql query paginating the results
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @param params the parameter map
	 * @param max
	 * @param offset
	 * @return the resultset
	 */
	 List<T> findAll(String hql,Map<String,Object> params,int max,int offset);
	
	/**
	 * Fetches all the results and the result count that matches the
	 * given hql query paginating the results
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @param params the parameter list
	 * @param max
	 * @param offset
	 * @return the resultset
	 */
	 List<T> findAll(String hql,Object[] params,int max,int offset);
	
	/**
	 * Same as findAllBy(criteriaBuilder,0,0);
	 * Fetches ALL the records matching the given criteria
	 * 
	 * @param criteriaBuilder - The criteria to be met (Filters, Orders)
	 * @return JPAResulset containing the fetched records and the total record count
	 */
	 ResultSet<T> findAll(AbstractCB criteriaBuilder);
	
	
	/**
	 * Fetches all the records matching the given criteria
	 * paginating the results
	 * 
	 * @param criteriaBuilder - The criteria to be met (Filters, Orders)
	 * @param max - The maximum number of records to be fetched
	 * @param offset - The first record position to be fetched
	 * @return JPAResulset containing the fetched records and the total record count
	 */
	 ResultSet<T> findAll(AbstractCB criteriaBuilder,int max,int offset);
	

	/**
	 * Same as countBy(new JPAFilterBuilder());
	 * Fetches the count of all the records
	 * @return the record count
	 */
	 Long count();
	
	
	/**
	 * Same as count(hql,new HashMap<String,Object>());
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @return the record count
	 */
	 Long count(String hql);
	
	/**
	 * Fetches the count of all the records matching the given hql query
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @param params the parameter map
	 * @return the record count
	 */
	 Long count(String hql,Map<String,Object> params);
	
	/**
	 * Fetches the count of all the records matching the given hql query
	 * @param hql - The input hql query, needs to begin with statement "from"
	 * 			    i.e. findAll("from Book where 1=1");
	 * @param params the parameter list
	 * @return the record count
	 */
	 Long count(String hql,Object[] params);
	
	
	/**
	 * Fetches the count of all the records matching the given criteria
	 * 
	 * @param filterBuilder - The criteria to be met
	 * @return
	 */
	 Long count(AbstractCB filterBuilder);

	/* Extras */
	
	/**
	 * @return the EntityManager
	 */
	 EntityManager getEntityManager();
	
}
