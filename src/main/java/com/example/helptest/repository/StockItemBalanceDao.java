package com.example.helptest.repository;

import com.example.helptest.model.Category;
import com.example.helptest.model.StockItemBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockItemBalanceDao extends CrudRepository<StockItemBalance, Integer> {
    List<StockItemBalance> findAllByCategory(Category category);

    Optional<StockItemBalance> findStockItemBalanceById(long id);
}
