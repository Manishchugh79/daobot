package com.github.hguerrerojaime.daobot.integration.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.hguerrerojaime.daobot.dao.GenericDAOImpl;

public class MyGenericDAOImpl extends GenericDAOImpl {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
