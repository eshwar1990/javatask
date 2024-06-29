package com.bansira.javatask.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * 
 * @author J Bharat Eshwar Reddy
 *
 */
@Entity
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	public String LibraryName;
	@OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
	public List<Department> departments;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLibraryName() {
		return LibraryName;
	}
	public void setLibraryName(String libraryName) {
		LibraryName = libraryName;
	}
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	
}
