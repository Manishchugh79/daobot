package com.github.hguerrerojaime.daobot.integration.dao;

import com.github.hguerrerojaime.daobot.dao.DAOImpl;
import com.github.hguerrerojaime.daobot.integration.eo.BookEO;

public class BookDAOImpl extends DAOImpl<BookEO, Long> implements BookDAO {

	public BookDAOImpl() {
		super(BookEO.class);
	}

}
