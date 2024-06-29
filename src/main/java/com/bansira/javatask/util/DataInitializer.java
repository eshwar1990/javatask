package com.bansira.javatask.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bansira.javatask.model.Book;
import com.bansira.javatask.model.Department;
import com.bansira.javatask.model.Library;
import com.bansira.javatask.respository.BookRepository;
import com.bansira.javatask.respository.DepartmentRespository;
import com.bansira.javatask.respository.LibraryRepository;

import jakarta.transaction.Transactional;

/**
 * 
 * @author J Bharat Eshwar Reddy
 *
 *	Java based loading of data to in-memory database
 */
@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private DepartmentRespository departmentRespository;
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {

		Book b1 = new Book();
		b1.setISBN("isbn 1");
		b1.setTitle("title 1");
		b1.setAuthor("author 1");
		b1.setGenre("genre 1");
		b1.setAvailability(true);
		b1.setPublicationYear(2000);
		b1.setDepartment(null);
		
		Book b2 = new Book();
		b2.setISBN("isbn 2");
		b2.setTitle("title 2");
		b2.setAuthor("author 2");
		b2.setGenre("genre 2");
		b2.setAvailability(true);
		b2.setPublicationYear(2001);
		b2.setDepartment(null);
		
		b1 = bookRepository.save(b1);
		b2 = bookRepository.save(b2);
		
		Department d1 = new Department();
		d1.setName("department 1");
		d1.setBooks(new ArrayList<>(Arrays.asList(b1)));
		d1.setLibrary(null);
		
		Department d2 = new Department();
		d2.setName("department 2");
		d2.setBooks(new ArrayList<>(Arrays.asList(b2)));
		d2.setLibrary(null);
		
		b1.setDepartment(d1);
		b2.setDepartment(d2);
		
		d1 = departmentRespository.save(d1);
		d2 = departmentRespository.save(d2);
		
		Library library = new Library();
		library.setLibraryName("some library Name");
		library.setDepartments(new ArrayList<>(Arrays.asList(d1,d2)));
		library = libraryRepository.save(library);
		
		d1.setLibrary(library);
		departmentRespository.save(d1);
		
		d2.setLibrary(library);
		departmentRespository.save(d2);
		
		b1 = bookRepository.save(b1);
		b2 = bookRepository.save(b2);	
		
	}

}
