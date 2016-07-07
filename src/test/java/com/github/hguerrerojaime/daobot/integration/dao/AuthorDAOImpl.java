package com.github.hguerrerojaime.daobot.integration.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.hguerrerojaime.daobot.dao.DAOImpl;
import com.github.hguerrerojaime.daobot.integration.eo.AuthorEO;

public class AuthorDAOImpl extends DAOImpl<AuthorEO, Long> implements AuthorDAO {

	@PersistenceContext
	private EntityManager em;
	
	public AuthorDAOImpl(){
		super(AuthorEO.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
