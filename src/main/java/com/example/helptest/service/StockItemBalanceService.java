package com.example.helptest.service;

import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.Category;
import com.example.helptest.model.StockItemBalance;
import com.example.helptest.repository.StockItemBalanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockItemBalanceService {
    @Autowired
    private StockItemBalanceDao stockItemBalanceDao;

    public StockItemBalance addItem(StockItemBalance stockItemBalance) {
        stockItemBalance.setId(stockItemBalanceDao.count() + 1);
        stockItemBalance.setInStockQty(0.0);
        stockItemBalance.setProductId(0);
        return stockItemBalanceDao.save(stockItemBalance);
    }

    public List<StockItemBalance> filter(Category category) {
        try {
            return stockItemBalanceDao.findAllByCategory(category);
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    public StockItemBalance getItem(long id) {
        try {
            return stockItemBalanceDao.findStockItemBalanceById(id).get();
        } catch (Exception ex) {
            throw new NotFoundException(ex.getLocalizedMessage());
        }

    }

    public StockItemBalance updateItem(long id, StockItemBalance stockItemBalance) {
        try {
            StockItemBalance oldItemBalance = stockItemBalanceDao.findStockItemBalanceById(id).get();
            if (oldItemBalance == null) throw new NotFoundException("Item is not found");
            stockItemBalance.setId(oldItemBalance.getId());
            return stockItemBalanceDao.save(stockItemBalance);
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    public boolean deleteItem(long id) {
        try {
            StockItemBalance stockItemBalance = stockItemBalanceDao.findStockItemBalanceById(id).get();
            if (stockItemBalance == null) throw new NotFoundException("Item is not found");
            stockItemBalanceDao.delete(stockItemBalance);
            return true;
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }
}
