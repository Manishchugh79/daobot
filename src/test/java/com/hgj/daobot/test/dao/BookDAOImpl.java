package com.hgj.daobot.test.dao;

import com.hgj.daobot.dao.DAOImpl;
import com.hgj.daobot.test.eo.BookEO;

public class BookDAOImpl extends DAOImpl<BookEO, Long> implements BookDAO {

	public BookDAOImpl() {
		super(BookEO.class);
	}

}
