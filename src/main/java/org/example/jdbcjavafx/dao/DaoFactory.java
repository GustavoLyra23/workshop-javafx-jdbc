package org.example.jdbcjavafx.dao;

import org.example.jdbcjavafx.db.DB;
import org.example.jdbcjavafx.dao.impl.DepartmentDaoJDBC;
import org.example.jdbcjavafx.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
