package com.example.helptest.service;

import com.example.helptest.exception.BigValueException;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.*;
import com.example.helptest.repository.StockDocumentDao;
import com.example.helptest.repository.StockInventoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockInventoryService {
    @Autowired
    private StockDocumentService stockDocumentService;

    @Autowired
    private StockInventoryDao stockInventoryDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockItemBalanceService stockItemBalanceService;

    public StockDocument addInventories(long docId, List<StockInventory> stockInventoryList) {
        //   try {
        StockDocument stockDocument = stockDocumentService.getDocument(docId);
        for (StockInventory stockInventory : stockInventoryList) {
            stockInventory.setStockDocument(stockDocument);
            stockInventory.setId(stockInventoryDao.count() + 1);
            if (stockDocument.getDocumentType().equals(DOCTYPE.IN.getName())) {
                in(stockInventory);
            } else {
                out(stockInventory);
            }
        }

        return stockDocumentService.getDocument(stockDocument.getDocumentId());
//        }catch (Exception ex){
//            throw new IllegalArgumentException(ex.getMessage());
//        }

    }

    private StockInventory in(StockInventory stockInventory) {
        try {
            StockItemBalance stockItemBalance = stockItemBalanceService.getItem(stockInventory.getProductId());

            if (stockItemBalance == null)
                throw new NotFoundException("The item " + stockInventory.getProductName() + " does not exists");

            stockInventory.setProductName(stockItemBalance.getName());

            stockItemBalance.setInStockQty(stockItemBalance.getInStockQty() + stockInventory.getAmount());
            stockItemBalanceService.updateItem(stockItemBalance.getId(), stockItemBalance);

            return stockInventoryDao.save(stockInventory);
        }catch (Exception ex){
            stockDocumentService.deleteDocument(stockInventory.getStockDocument());
            throw new NotFoundException(ex.getMessage());
        }
    }

    private void out(StockInventory stockInventory) {
        try {
            StockItemBalance stockItemBalance = stockItemBalanceService.getItem(stockInventory.getProductId());
            Product product = null;
            if (stockItemBalance.getProductId() == 0) {
                product = productService.addProduct(stockItemBalance);
                stockItemBalance.setProductId(product.getId());
            } else {
                product = productService.getProduct(stockItemBalance.getProductId());
            }

            if (stockItemBalance.getInStockQty() < stockInventory.getAmount()){
                stockDocumentService.deleteDocument(stockInventory.getStockDocument());
                throw new BigValueException("There is not enough " + stockItemBalance.getName() + " in stock");
            }


            stockItemBalance.setInStockQty(stockItemBalance.getInStockQty() - stockInventory.getAmount());

            product.setInStockQty(product.getInStockQty() + stockInventory.getAmount());
            productService.updateProduct(product.getId(), product);

            stockInventoryDao.save(stockInventory);
        }catch (Exception ex){
            stockDocumentService.deleteDocument(stockInventory.getStockDocument());
            throw new NotFoundException(ex.getMessage());
        }
    }
}
