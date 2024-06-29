package com.bansira.javatask.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bansira.javatask.model.Book;

/**
 * 
 * @author J Bharat Eshwar Reddy
 *
 *repository class for queries on books
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	Optional<Book> findByISBN(String isbn);
	void deleteByISBN(String isbn);
	List<Book> findByTitleIgnoreCaseContaining(String title);
    List<Book> findByAuthorIgnoreCaseContaining(String author);
}
