package com.github.hguerrerojaime.daobot.integration.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.hguerrerojaime.daobot.dao.DAOImpl;
import com.github.hguerrerojaime.daobot.integration.eo.BookEO;

public class BookDAOImpl extends DAOImpl<BookEO, Long> implements BookDAO {

	@PersistenceContext
	private EntityManager em;
	
	public BookDAOImpl() {
		super(BookEO.class);
	}

	@Override
	public EntityManager getEntityManager() {
		
		
		return em;
	}

}
