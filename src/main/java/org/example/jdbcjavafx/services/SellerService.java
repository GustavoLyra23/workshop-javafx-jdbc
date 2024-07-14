package org.example.jdbcjavafx.services;


import org.example.jdbcjavafx.dao.DaoFactory;
import org.example.jdbcjavafx.dao.SellerDao;
import org.example.jdbcjavafx.entities.Seller;

import java.util.List;

public class SellerService {

    private final SellerDao dao = DaoFactory.createSellerDao();


    public List<Seller> getAllSellers() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller seller) {
        if (seller.getId() == null) {
            dao.insert(seller);
        } else {
            dao.update(seller);
        }
    }

    public void remove(Seller seller) {
        dao.deleteById(seller.getId());
    }

}
