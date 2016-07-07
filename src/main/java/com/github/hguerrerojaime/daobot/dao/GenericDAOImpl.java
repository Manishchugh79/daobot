package com.github.hguerrerojaime.daobot.dao;

import java.io.Serializable;

import com.github.hguerrerojaime.daobot.core.AbstractCB;
import com.github.hguerrerojaime.daobot.core.CB;
import com.github.hguerrerojaime.daobot.core.DynamicQueryBuilder;
import com.github.hguerrerojaime.daobot.core.QueryGenerator;
import com.github.hguerrerojaime.daobot.core.ResultSet;
import com.github.hguerrerojaime.daobot.eo.EntityObject;

public abstract class GenericDAOImpl implements GenericDAO {

    @Override
    public <T extends EntityObject<K>, K extends Serializable> T get(
            Class<T> entityClass, K id) {
        return getEntityManager().find(entityClass, id);
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
            Class<T> entityClass, AbstractCB criteriaBuilder) {
        return getQueryGenerator(entityClass).build(criteriaBuilder, 1, 0)
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
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass, AbstractCB criteriaBuilder) {
        return findAll(entityClass, criteriaBuilder, 0, 0);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> ResultSet<T> findAll(
            Class<T> entityClass, AbstractCB criteriaBuilder, int max, int offset) {
        return getQueryGenerator(entityClass).build(criteriaBuilder, max, offset);
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass) {
        return count(entityClass, new CB());
    }

    @Override
    public <T extends EntityObject<K>, K extends Serializable> Long count(
            Class<T> entityClass, AbstractCB filterBuilder) {
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
	public <T extends EntityObject<K>, K extends Serializable> DynamicQueryBuilder<T, K> from(Class<T> entityClass) {
		return new DynamicQueryBuilder<T, K>(getEntityManager(), entityClass);
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

}
