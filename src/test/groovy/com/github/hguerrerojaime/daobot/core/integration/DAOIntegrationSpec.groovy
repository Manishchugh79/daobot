package com.github.hguerrerojaime.daobot.core.integration

import org.junit.Test;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration

import com.github.hguerrerojaime.daobot.core.CB
import com.github.hguerrerojaime.daobot.dao.GenericDAO;
import com.github.hguerrerojaime.daobot.integration.dao.BookDAO
import com.github.hguerrerojaime.daobot.integration.eo.BookEO;

import static com.github.hguerrerojaime.daobot.helpers.ExpressionBuilderHelper.path;

import spock.lang.Specification
import spock.lang.Unroll;

@ContextConfiguration(locations=["classpath:META-INF/applicationContext.xml"])
class DAOIntegrationSpec extends Specification {
	
	@Autowired
	BookDAO bookDAO
	
	@Autowired
	GenericDAO genericDAO
	
	@Unroll
	def "validate find book expecting null = #isNull where title eq = #title"(String title, boolean isNull) {
		
		given: "A book variable from the database"
			def book = bookDAO.find(new CB()
				.eq("title",title)
			)
		
		expect: "The book object is null if it doesn't exist in the database"
			(book == null) == isNull
		where:
			title << ["ornare, lectus","dont exist","purus.","no existo"]
			isNull << [false,true,false,true]
		
	}
	
	@Unroll
	def "validate find book expecting null = #isNull where author name like #author"(String author, boolean isNull) {
		
		given: "A book variable from the database"
			def book = bookDAO.find(new CB()
				.join("author", new CB()
					.ilike("name",author)	
				)
			)
		
		expect: "The book object is null if it doesn't exist in the database"
			(book == null) == isNull
		where:
			author << ["%drew%","%urs%","%notaperson%","%riv%"]
			isNull << [false,false,true,false]
		
	}
	
	def "validate find all books where book title = ornare, lectus or purus."() {
		
		given: "A book variable from the database"
			def rs = bookDAO.findAll(new CB()
				.or(new CB()
					.eq("title", "ornare, lectus")
					.eq("title", "purus.")
				)
			)
		
		expect: "The book object is null if it doesn't exist in the database"
			 rs.count() == 2
			 rs.list().size() == 2

		
	}
	
	def "validate find all books"() {
		
		given: "A book variable from the database"
			def rs = bookDAO.findAll(2,0)
		
		expect: "The book object is null if it doesn't exist in the database"
			 rs.list().size() == 2
			 rs.count() > 2
	}
	
	def "validate custom select from generic dao"() {
		
		given: "A book variable from the database"
			def rs = genericDAO.from(BookEO).findAll()
		
		expect: "The book object is null if it doesn't exist in the database"
			 rs.list().size() == 100
			 rs.count() == 100
	}
	
	def "validate custom select from generic dao selecting titles"() {
		
		given: "A book variable from the database"
			def rs = genericDAO.from(BookEO)
							.select(path("title"))
							.findAll()
		
		expect: "The book object is null if it doesn't exist in the database"
			 def list = rs.list()
		
			 list.size() == 100
			 rs.count() == 100
	}
	
	def "validate custom select from book dao selecting titles"() {
		
		given: "A book variable from the database"
			def rs = bookDAO.fromEntity()
							.select(path("title"))
							.withCriteria(new CB()
								.like("title", "ornare%")
							)
							.findAll()
		
		expect: "The book object is null if it doesn't exist in the database"
			 def list = rs.list()
		
			 list.size() == 2
			 rs.count() == 2
	} 

}
