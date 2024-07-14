package org.example.jdbcjavafx.services;


import org.example.jdbcjavafx.dao.DaoFactory;
import org.example.jdbcjavafx.dao.DepartmentDao;
import org.example.jdbcjavafx.entities.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();


    public List<Department> getAllDepartments() {
        return dao.findAll();
    }

    public void saveOrUpdate(Department department) {
        if (department.getId() == null) {
            dao.insert(department);
        } else {
            dao.update(department);
        }
    }

    public void remove(Department department) {
        dao.deleteById(department.getId());
    }

}
