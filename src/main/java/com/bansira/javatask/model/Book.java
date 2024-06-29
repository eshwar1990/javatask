package com.bansira.javatask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 
 * @author J Bharat Eshwar Reddy
 *
 */
@Entity
public class Book {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ISBN;
    private String title;
    private String author;
    private String genre;
    private Integer publicationYear;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private boolean availability;
    
    public Book() {
    }
    
    public Book(String title, String author, String isbn, String genre, Integer publicationYear, boolean available, Department department) {
        this.title = title;
        this.author = author;
        this.ISBN = isbn;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.availability = available;
        this.department = department;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Integer getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
    

}
