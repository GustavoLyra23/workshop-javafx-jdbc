package services;

import entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "Books"));
        departments.add(new Department(2, "Computers"));
        departments.add(new Department(3, "Eletronics"));
        return departments;
    }


}
