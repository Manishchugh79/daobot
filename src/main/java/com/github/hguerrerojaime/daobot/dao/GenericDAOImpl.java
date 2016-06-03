package com.github.hguerrerojaime.daobot.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.github.hguerrerojaime.daobot.core.CB;
import com.github.hguerrerojaime.daobot.core.ResultSet;
import com.github.hguerrerojaime.daobot.core.JsonCB;
import com.github.hguerrerojaime.daobot.core.QueryGenerator;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

public class GenericDAOImpl implements GenericDAO {

    @PersistenceContext
    private EntityManager em;

    public GenericDAOImpl() {

    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> T get(
            Class<T> entityClass, K id) {
        return em.find(entityClass, id);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> boolean exists(
            Class<T> entityClass, K id) {
        return get(entityClass, id) != null;
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> T find(
            Class<T> entityClass) {
        return find(entityClass, new CB());
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> T find(
            Class<T> entityClass, String hql) {
        return (T) find(entityClass, hql, new HashMap<String, Object>());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends EntityObject<K>, K extends Serializable> T find(
            Class<T> entityClass, String hql, Map<String, Object> params) {
        Query query = generateHQLQuery(entityClass, hql);

        addParametersToQuery(query, params);

        query.setMaxResults(1);
        query.setFirstResult(0);

        return (T) query.getSingleResult();
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> T find(
            Class<T> entityClass, CB criteriaBuilder) {
        return getQueryGenerator(entityClass).build(criteriaBuilder, 1, 0, false)
                .get();
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass) {
        return findAll(entityClass, 0, 0);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass, int max, int offset) {
        return findAll(entityClass, new CB(), max, offset);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> List<T> findAll(
            Class<T> entityClass, String hql) {
        return findAll(entityClass, hql, 0, 0);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> List<T> findAll(
            Class<T> entityClass, String hql, int max, int offset) {
        return findAll(entityClass, hql, new HashMap<String, Object>(), max,
                offset);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> List<T> findAll(
            Class<T> entityClass, String hql, Map<String, Object> params) {
        return findAll(entityClass, hql, params, 0, 0);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> List<T> findAll(
            Class<T> entityClass, String hql, Object[] params) {
        return findAll(entityClass, hql, params, 0, 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends EntityObject<K>, K extends Serializable> List<T> findAll(
            Class<T> entityClass, String hql, Map<String, Object> params,
            int max, int offset) {
        Query query = generateHQLQuery(entityClass, hql);

        addParametersToQuery(query, params);

        paginateQuery(query, max, offset);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends EntityObject<K>, K extends Serializable> List<T> findAll(
            Class<T> entityClass, String hql, Object[] params, int max,
            int offset) {
        Query query = generateHQLQuery(entityClass, hql);

        addParametersToQuery(query, params);
        paginateQuery(query, max, offset);

        return query.getResultList();
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass, CB criteriaBuilder) {
        return findAll(entityClass, criteriaBuilder, 0, 0);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass, CB criteriaBuilder, int max, int offset) {
        return getQueryGenerator(entityClass).build(criteriaBuilder, max, offset);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass) {
        return count(entityClass, new FB());
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass, String hql) {
        return count(entityClass, hql, new HashMap<String, Object>());
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass, String hql, Map<String, Object> params) {
        StringBuilder countHQL = new StringBuilder();
        countHQL.append("select count(").append(entityClass.getSimpleName())
                .append(") ").append(hql);

        Query countQuery = getEntityManager().createQuery(countHQL.toString());

        addParametersToQuery(countQuery, params);

        return (Long) countQuery.getSingleResult();
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass, String hql, Object[] params) {

        StringBuilder countHQL = new StringBuilder();
        countHQL.append("select count(").append(entityClass.getSimpleName())
                .append(") ").append(hql);

        Query countQuery = getEntityManager().createQuery(countHQL.toString());

        addParametersToQuery(countQuery, params);

        return (Long) countQuery.getSingleResult();
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass, FB filterBuilder) {
        return getQueryGenerator(entityClass).getCount(filterBuilder);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> T save(
            T instance) {
        return save(instance, false);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> T save(
            T instance, boolean flush) {

        if (instance == null) {
            throw new IllegalArgumentException(
                    "Entity Instance cannot be null");
        }

        if (instance.getId() == null) {
            getEntityManager().persist(instance);
        }
        else {
            getEntityManager().merge(instance);
        }

        if (flush) {
            flush();
        }

        return instance;
    }

    @Override
    public void flush() {
        getEntityManager().flush();
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> K delete(
            T instance) {
        K id = instance.getId();
        getEntityManager().remove(instance);
        return id;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public <T extends EntityObject<K>, K extends Serializable> T find(
            Class<T> entityClass, JsonCB criteriaBuilder) {
      
        return getQueryGenerator(entityClass).build(criteriaBuilder, 1, 0, false).get();
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass, JsonCB criteriaBuilder) {
        return getQueryGenerator(entityClass).build(criteriaBuilder, 0, 0, true);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass, JsonCB criteriaBuilder, int max,
            int offset) {
        return getQueryGenerator(entityClass).build(criteriaBuilder, max, offset, true);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass, JsonFB filterBuilder) {
        return getQueryGenerator(entityClass).getCount(filterBuilder);
    }

    private <T extends EntityObject<K>, K extends Serializable> QueryGenerator<T, K> getQueryGenerator(
            Class<T> entityClass) {

        QueryGenerator<T, K> queryGenerator = null;

        if (getEntityManager() != null) {
            queryGenerator = new QueryGenerator<T, K>(getEntityManager(),
                    entityClass);
        }

        return queryGenerator;
    }

    /**
     * Generates the complete HQL query for the find methods that use HQL
     * 
     * @param hql
     * @return
     */
    private <T extends EntityObject<K>, K extends Serializable> Query generateHQLQuery(
            Class<T> entityClass, String hql) {

        return getEntityManager().createQuery(hql);

    }

    /**
     * Adds the parameters for the find methods that use HQL
     * 
     * @param query
     * @param params
     * @return the Query object
     */
    private Query addParametersToQuery(Query query,
            Map<String, Object> params) {

        Iterator<String> it = params.keySet().iterator();

        while (it.hasNext()) {
            String paramKey = it.next();
            Object paramValue = params.get(paramKey);

            query.setParameter(paramKey, paramValue);
        }

        return query;
    }

    /**
     * Adds the parameters for the find methods that use HQL
     * 
     * @param query
     * @param params
     * @return the Query object
     */
    private Query addParametersToQuery(Query query, Object[] params) {

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        return query;
    }

    /**
     * Configures pagination variables for the query
     * 
     * @param query
     *            the query to be paginated
     * @param max
     *            the max number of records
     * @param offset
     *            the first record
     * @return the paginated query object
     */
    private Query paginateQuery(Query query, int max, int offset) {

        if (max > 0 && max <= GenericDAO.MAX_RECORDS) {
            query.setMaxResults(max);
        }
        else {
            query.setMaxResults(GenericDAO.MAX_RECORDS);
        }

        if (offset >= 0) {
            query.setFirstResult(offset);
        }

        return query;

    }

}
