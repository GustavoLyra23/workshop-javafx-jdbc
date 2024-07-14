package org.example.jdbcjavafx.dao;

import java.util.List;

import org.example.jdbcjavafx.entities.Department;
import org.example.jdbcjavafx.entities.Seller;

public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
}
