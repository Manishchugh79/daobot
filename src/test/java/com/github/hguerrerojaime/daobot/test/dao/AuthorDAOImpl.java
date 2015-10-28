package com.github.hguerrerojaime.daobot.test.dao;

import com.github.hguerrerojaime.daobot.dao.DAOImpl;
import com.github.hguerrerojaime.daobot.test.eo.AuthorEO;

public class AuthorDAOImpl extends DAOImpl<AuthorEO, Long> implements AuthorDAO {

	public AuthorDAOImpl(){
		super(AuthorEO.class);
	}

}
