package com.bansira.javatask.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.bansira.javatask.dto.BookDTO;
import com.bansira.javatask.model.Book;
import com.bansira.javatask.model.Department;
import com.bansira.javatask.respository.BookRepository;
import com.bansira.javatask.respository.DepartmentRespository;


@RunWith(MockitoJUnitRunner.class) // Use MockitoJUnitRunner for JUnit 4 with Mockito
@SpringBootTest
public class LibraryServiceTests {

    @Mock
    private BookRepository bookRepository; // Mocked repository
    
    @Mock
    private DepartmentRespository departmentRespository;

    @InjectMocks
    private LibraryService libraryService; // Inject the service under test

    @Before
    public void setUp() {
        // Mock behavior of bookRepository
        String author = "author 1";
        Department d = new Department();
        d.setId(1L);
        Book book1 = new Book( "title 1", "author 1","isbn 1", "genre 1", 2000, true, d);
        Book book2 = new Book( "title 2", "author 1","isbn 2", "genre 2", 2001, true, d);
        Book book3 = new Book( "title 3", "author 2","isbn 3", "genre 3", 2003, false, d);
        when(bookRepository.findByAuthorIgnoreCaseContaining(author)).thenReturn(Arrays.asList(book1, book2));
        when(bookRepository.findByTitleIgnoreCaseContaining("title 1")).thenReturn(Arrays.asList(book1));
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1,book2,book3));
        doNothing().when(bookRepository).deleteByISBN("isbn 1");
    	when(bookRepository.findByISBN("isbn 4")).thenReturn(Optional.empty());
    	when(departmentRespository.findById(1L)).thenReturn(Optional.of(d));
    	
    	
    }

    @Test
    public void testFindBookByAuthor() {
        // Act
        List<BookDTO> books = libraryService.findBookByAuthor("author 1");

        // Assert
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("isbn 1", books.get(0).getIsbn());
        assertEquals("isbn 2", books.get(1).getIsbn());
    }
    
    @Test
    public void testFindBookByTitle() {
        // Act
        List<BookDTO> books = libraryService.findBookByTitle("title 1");

        // Assert
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("isbn 1", books.get(0).getIsbn());
    }
    
    @Test
    public void testFindAll() {
        // Act
        List<BookDTO> books = libraryService.listAllBooks();

        // Assert
        assertNotNull(books);
        assertEquals(3, books.size());
    }
    
    @Test
    public void testFindAllAvailable() {
    	
        // Act
        List<BookDTO> books = libraryService.listAvailableBooks();

        // Assert
        assertNotNull(books);
        assertEquals(2, books.size());
    }
    
    @Test
    public void testDeleteIsbn() {
    	
        // Act
    	libraryService.removeBook("isbn 1");
    	// Assert (verify that deleteByISBN was called with the correct argument)
        verify(bookRepository,times(1)).deleteByISBN("isbn 1");
    }
    
    @Test
    public void testBookSave() {
    	//Act
    	BookDTO bookDTO = new BookDTO();
    	bookDTO.setAuthor("author 4");
    	bookDTO.setAvailable(true);
    	bookDTO.setDepartmentId(1L);
    	bookDTO.setGenre("genre 4");
    	bookDTO.setIsbn("isbn 4");
    	bookDTO.setPublicationYear(2004);
    	bookDTO.setTitle("title 4");
    	
    	
    	String result = libraryService.addBook(bookDTO);
    	assertEquals("Book added successfully.",result);
        
    }
}