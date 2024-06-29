package com.bansira.javatask.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bansira.javatask.model.Library;

/**
 * 
 * @author J Bharat Eshwar Reddy
 *
 *	Respository class for queries on Library
 */
@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

}
