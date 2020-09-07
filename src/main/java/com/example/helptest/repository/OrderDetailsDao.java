package com.example.helptest.repository;

import com.example.helptest.model.OrderDetail;
import com.example.helptest.model.Orders;
import com.example.helptest.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsDao extends CrudRepository<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrderAndAndProduct(Orders orders, Product product);

}
