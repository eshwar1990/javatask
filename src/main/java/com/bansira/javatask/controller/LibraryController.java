package com.bansira.javatask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bansira.javatask.dto.BookDTO;
import com.bansira.javatask.service.LibraryService;

/**
 * 
 * @author J Bharat Eshwar Reddy
 *
 *Controller class for RestFul web service 
 */
@RestController
@RequestMapping("/books")
public class LibraryController {
	
    @Autowired
    private LibraryService libraryService;
    
    /**
     * 
     * @param bookDTO
     * @return returns the success message or failure message with reason
     * failure reasons are: if ISBN is already exists, Department ID not found
     * 
     */
    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody BookDTO bookDTO) {
        String result = libraryService.addBook(bookDTO);
        if ("Book added successfully.".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 
     * @param isbn
     * removes the book based on ISBN number
     */
    @DeleteMapping("/remove")
    public void removeBook(@RequestParam("isbn") String isbn) {
        libraryService.removeBook(isbn);
    }

    /**
     * 
     * @param title
     * @return return list of books that match the title in a case insensitive manner
     */
    @GetMapping("/title")
    public List<BookDTO> findBookByTitle(@RequestParam("name") String title) {
        return libraryService.findBookByTitle(title);
    }

    /**
     * 
     * @param author
     * @return return the list of books that match the author name in case insensitive manner
     */
    @GetMapping("/author")
    public List<BookDTO> findBookByAuthor(@RequestParam("name") String author) {
        return libraryService.findBookByAuthor(author);
    }

    /**
     * 
     * @return list of all books regardless of availability status
     */
    @GetMapping
    public List<BookDTO> listAllBooks() {
        return libraryService.listAllBooks();
    }

    /**
     * 
     * @return list of all books based on availability status
     */
    @GetMapping("/available")
    public List<BookDTO> listAvailableBooks() {
        return libraryService.listAvailableBooks();
    }
    
}
