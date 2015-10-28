package com.github.hguerrerojaime.daobot.test.eo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.hguerrerojaime.daobot.eo.EntityObject;


@Entity
@Table(name="book")
public class BookEO implements EntityObject<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5077023850679810561L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@Column(name="units_sold")
	private Integer unitsSold;
	
	@Column(name="units_bought")
	private Integer unitsBought;
	
	@ManyToOne
	@JoinColumn(name="author_id")
	private AuthorEO author;
	
	
	
	

	/**
	 * @return the author
	 */
	public AuthorEO getAuthor() {
		return author;
	}



	/**
	 * @param author the author to set
	 */
	public void setAuthor(AuthorEO author) {
		this.author = author;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Date getReleaseDate() {
		return releaseDate;
	}



	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}



	public void setId(Long id) {
		this.id = id;
	}



	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}



	public Integer getUnitsSold() {
		return unitsSold;
	}



	public void setUnitsSold(Integer unitsSold) {
		this.unitsSold = unitsSold;
	}



	public Integer getUnitsBought() {
		return unitsBought;
	}



	public void setUnitsBought(Integer unitsBought) {
		this.unitsBought = unitsBought;
	}


}
