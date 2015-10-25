package com.hgj.daobot.test.dao;

import com.hgj.daobot.dao.DAOImpl;
import com.hgj.daobot.test.eo.AuthorEO;

public class AuthorDAOImpl extends DAOImpl<AuthorEO, Long> implements AuthorDAO {

	public AuthorDAOImpl(){
		super(AuthorEO.class);
	}

}
