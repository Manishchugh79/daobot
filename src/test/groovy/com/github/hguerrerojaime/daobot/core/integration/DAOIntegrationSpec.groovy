package com.github.hguerrerojaime.daobot.core.integration

import org.junit.Test;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional;

import com.github.hguerrerojaime.daobot.core.CB
import com.github.hguerrerojaime.daobot.core.Order;
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
	
	def "validate find all books where book is not 1 nor 2"() {
		
		given: "A book variable from the database"
			def rs = bookDAO.findAll(new CB()
				.not(new CB()
					.or(new CB()
						.eq("id",1L)
						.eq("id",2L)
					)
				)
			)
		
		expect: "The book object is null if it doesn't exist in the database"
			 rs.get().id != 1L && rs.get().id != 2L

		
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
	
	def "validate that record exists"() {
		
		given: "An id"
		Long id = 1L
		expect: "record exists"
		bookDAO.exists(id)
		
	}
	
	def "validate that record does not exist"() {
		
		given: "An id"
		Long id = 0L
		expect: "record does not exist"
		!bookDAO.exists(id)
		
	}
	
	def "validate that record is not null"() {
		
		given: "An id"
		Long id = 1L
		expect: "record is not null"
		bookDAO.get(id) != null
		
	}
	
	def "validate find all"() {
		
		given: "An id"
		Long id = 1L
		expect: "record is not null"
		bookDAO.findAll().count() > 0
		
	}
	
	def "validate find all sort by id"() {
		
		given: "An expected id"
		Long id = 1L
		expect: "record is not null"
		bookDAO.findAll(new CB().sort("id")).get().id == id
		
	}
	
	def "validate find all with arguments"() {
		
		given: "An id"
		Long id = 1L
		expect: "record is not null"
		bookDAO.findAll(new CB(),0,0).count() > 0
		
	}

	
	def "validate count"() {
		
		given: "An id"
		Long id = 1L
		expect: "record is not null"
		bookDAO.count() > 0
		
	}
	
	def "validate count with filter arguments"() {
		
		given: "An id"
		Long id = 1L
		expect: "record is not null"
		bookDAO.count(new CB()) > 0
		
	}
	
	def "validate find"() {
		expect: "record is not null"
		bookDAO.find() != null
		
	}
	
	@Transactional
	def "validate save"() {
		given: "a mock book"
		
		BookEO b = new BookEO()
		b.title = "Mock book"
		b.description = "Desc"
		b.releaseDate = new Date()
		
		expect: "record is not null"
	
		bookDAO.save(b) != null
		
		and: "record has an id"
		
		b.id != null
	}
	
	@Transactional
	def "validate update"() {
		given: "a mock book"
		
		BookEO b = bookDAO.find()
		b.title = "Mock book"
		b.description = "Desc"
		b.releaseDate = new Date()
		
		expect: "record is not null"
	
		bookDAO.save(b,true) != null
		
		and: "record has an id"
		
		b.id != null
	}
	
	@Transactional
	def "validate save null instance"() {

		
		when: "saving a null entity"
		bookDAO.save(null)
		
		then: "exception is thrown"
		thrown IllegalArgumentException

	}
	
	@Transactional
	def "validate delete"() {
		given: "a mock book"
		
		BookEO b = bookDAO.find(new CB().sort("id",Order.DESC))
		
		expect: "record is not null"
	
		bookDAO.delete(b) != null

	}
}
