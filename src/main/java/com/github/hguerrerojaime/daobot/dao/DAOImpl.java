package com.github.hguerrerojaime.daobot.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.hguerrerojaime.daobot.core.AbstractCB;
import com.github.hguerrerojaime.daobot.core.CB;
import com.github.hguerrerojaime.daobot.core.ResultSet;
import com.github.hguerrerojaime.daobot.core.JsonCB;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

/**
 * This is the implementation for the generic DAO
 * 
 * @author Humberto Guerrero Jaime
 *
 * @param <T>
 *            the EntityObject type
 * @param <K>
 *            the Primary Key for the Entity Object
 */
public class DAOImpl<T extends EntityObject<K>, K extends Serializable>
		extends GenericDAOImpl implements DAO<T, K> {

	//
	// instance variables
	//
	private Class<T> entityClass;

	//
	// constructor
	//

	/**
	 * Constructor
	 * 
	 * @param entityClass
	 *            the class of the Entity Object
	 */
	public DAOImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public T get(K id) {
		return super.get(entityClass, id);
	}

	@Override
	public boolean exists(K id) {
		return super.exists(entityClass,id);
	}

	@Override
	public T find() {
		return super.find(entityClass);
	}

	@Override
	public T find(String hql) {
		return super.find(entityClass,hql);
	}

	@Override
	public T find(String hql, Map<String, Object> params) {
		return super.find(entityClass,hql,params);
	}

	@Override
	public T find(CB criteriaBuilder) {
		return super.find(entityClass,criteriaBuilder);
	}

	@Override
	public ResultSet<T> findAll() {
		return super.findAll(entityClass);
	}

	@Override
	public ResultSet<T> findAll(int max, int offset) {
		return super.findAll(entityClass,max,offset);
	}

	@Override
	public List<T> findAll(String hql) {
		return super.findAll(entityClass,hql);
	}

	@Override
	public List<T> findAll(String hql, int max, int offset) {
		return super.findAll(entityClass,hql,max,offset);
	}

	@Override
	public List<T> findAll(String hql, Map<String, Object> params) {
		return super.findAll(entityClass,hql,params);
	}

	@Override
	public List<T> findAll(String hql, Object[] params) {
		return super.findAll(entityClass,hql,params);
	}

	@Override
	public List<T> findAll(String hql, Map<String, Object> params, int max,
			int offset) {
		return super.findAll(entityClass,hql,params,max,offset);
	}

	@Override
	public List<T> findAll(String hql, Object[] params, int max, int offset) {
		return super.findAll(entityClass,hql,params,max,offset);
	}

	@Override
	public ResultSet<T> findAll(AbstractCB criteriaBuilder) {
		return super.findAll(entityClass,criteriaBuilder);
	}

	@Override
	public ResultSet<T> findAll(AbstractCB criteriaBuilder, int max, int offset) {
		return super.findAll(entityClass,criteriaBuilder,max,offset);
	}

	@Override
	public Long count() {
		return super.count(entityClass);
	}

	@Override
	public Long count(String hql) {
		return super.count(entityClass,hql);
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		return super.count(entityClass,hql,params);
	}

	@Override
	public Long count(String hql, Object[] params) {
		return super.count(entityClass,hql,params);
	}

	@Override
	public Long count(AbstractCB filterBuilder) {
		return super.count(entityClass,filterBuilder);
	}

}
