package com.github.hguerrerojaime.daobot.integration.dao;

import com.github.hguerrerojaime.daobot.dao.DAOImpl;
import com.github.hguerrerojaime.daobot.integration.eo.AuthorEO;

public class AuthorDAOImpl extends DAOImpl<AuthorEO, Long> implements AuthorDAO {

	public AuthorDAOImpl(){
		super(AuthorEO.class);
	}

}
