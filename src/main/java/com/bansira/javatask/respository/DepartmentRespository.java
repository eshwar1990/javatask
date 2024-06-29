package com.bansira.javatask.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bansira.javatask.model.Department;

/**
 * 
 * @author J Bharat Eshwar Reddy
 *
 *	repository class for queries on departments
 */
@Repository
public interface DepartmentRespository extends JpaRepository<Department, Long> {

}
