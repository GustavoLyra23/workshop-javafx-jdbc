package org.example.jdbcjavafx.dao;

import java.util.List;

import org.example.jdbcjavafx.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
