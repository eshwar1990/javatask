package com.bansira.javatask.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bansira.javatask.dto.BookDTO;
import com.bansira.javatask.model.Book;
import com.bansira.javatask.model.Department;
import com.bansira.javatask.respository.BookRepository;
import com.bansira.javatask.respository.DepartmentRespository;

import jakarta.transaction.Transactional;

/**
 * 
 * @author J Bharat Eshwar Reddy
 * 
 * Service class with business logic for actions on books
 *
 */
@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DepartmentRespository departmentRepository;
    
    /**
     * 
     * @param bookDTO
     * @return success or failure message
     */
    @Transactional
    public String addBook(BookDTO bookDTO) {
        // Check if a book with the same ISBN already exists
        Optional<Book> existingBook = bookRepository.findByISBN(bookDTO.getIsbn());
        if (existingBook.isPresent()) {
            return "Book with this ISBN already exists.";
        }

        // Find the department by ID
        Optional<Department> departmentOpt = departmentRepository.findById(bookDTO.getDepartmentId());
        if (!departmentOpt.isPresent()) {
            return "Department not found.";
        }

        Department department = departmentOpt.get();

        // Create and save the new book
        Book newBook = new Book(
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getIsbn(),
                bookDTO.getGenre(),
                bookDTO.getPublicationYear(),
                bookDTO.isAvailable(),
                department
        );
        bookRepository.save(newBook);

        return "Book added successfully.";
    }

    /**
     * 
     * @param ISBN
     * delete the book
     */
    @Transactional
    public void removeBook(String ISBN) {
        bookRepository.deleteByISBN(ISBN);
    }

    /**
     * 
     * @param title
     * @return list of books with title that matches
     */
    public List<BookDTO> findBookByTitle(String title) {
    	List<BookDTO> allBooks = convertIteratorToListBookDTO(bookRepository.findByTitleIgnoreCaseContaining(title).iterator());
     	 
        return allBooks;
       
    }

    /**
     * 
     * @param author
     * @return list of books with author name that matches
     */
    public List<BookDTO> findBookByAuthor(String author) {
    	
    	List<BookDTO> allBooks = convertIteratorToListBookDTO(bookRepository.findByAuthorIgnoreCaseContaining(author).iterator());
      	 
        return allBooks;
    	
    }

    /**
     * 
     * @return list of books
     */
    public List<BookDTO> listAllBooks() {
    	List<BookDTO> allBooks = convertIteratorToListBookDTO(bookRepository.findAll().iterator());
   	 
        return allBooks;
    }

    /**
     * 
     * @return list of available books
     */
    public List<BookDTO> listAvailableBooks() {
    	 
    	 List<BookDTO> allBooks = convertIteratorToListBookDTO(bookRepository.findAll().iterator());
    	 
        return allBooks.stream().filter(BookDTO::isAvailable).toList();
    }
    
    /**
     * 
     * @param iter1
     * @return 
     * Helper method to convert List of Book model to List of DTO
     */
    private List<BookDTO> convertIteratorToListBookDTO(Iterator<Book> iter1) {
    	List<BookDTO> allBooks = new ArrayList<>();
    	
    	while(iter1.hasNext()) {
    	Book cur = iter1.next();
    	BookDTO bdto = convertBookToBookDto(cur);
    	allBooks.add(bdto);
    	}
    	return allBooks;
    }
    
    /**
     * 
     * @param book
     * @return
     * Helper class to convert Book model to DTO
     */
    private BookDTO convertBookToBookDto(Book book) {
    	BookDTO bookDTO = new BookDTO();
    	bookDTO.setAuthor(book.getAuthor());
    	bookDTO.setAvailable(book.isAvailability());
    	bookDTO.setDepartmentId(book.getDepartment().getId());
    	bookDTO.setGenre(book.getGenre());
    	bookDTO.setIsbn(book.getISBN());
    	bookDTO.setPublicationYear(book.getPublicationYear());
    	bookDTO.setTitle(book.getTitle());
    	
    	return bookDTO;
    }
    
}
