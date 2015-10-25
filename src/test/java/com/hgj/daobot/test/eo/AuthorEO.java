package com.hgj.daobot.test.eo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hgj.daobot.eo.EntityObject;

@Entity
@Table(name="author")
public class AuthorEO implements EntityObject<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="birth_date")
	private Date birthDate;
	
	@OneToMany(mappedBy="author",fetch=FetchType.LAZY)
	private List<BookEO> books;
	
	
	public AuthorEO(){
		books = new ArrayList<BookEO>();
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}




	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}




	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}




	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}




	/**
	 * @return the books
	 */
	public List<BookEO> getBooks() {
		return books;
	}




	/**
	 * @param books the books to set
	 */
	public void setBooks(List<BookEO> books) {
		this.books = books;
	}




	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}




	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	

}
