package com.example.helptest.repository;

import com.example.helptest.model.StockInventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInventoryDao extends CrudRepository<StockInventory, Integer> {
}
