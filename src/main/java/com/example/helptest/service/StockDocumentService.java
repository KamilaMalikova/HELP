package com.example.helptest.service;

import com.example.helptest.config.LocalPagination;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.exception.ImpossibleConditionException;
import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.DOCTYPE;
import com.example.helptest.model.StockDocument;
import com.example.helptest.repository.StockDocumentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Service
public class StockDocumentService {

    @Autowired
    private StockDocumentDao stockDocumentDao;

    public Page<StockDocument> filter(int page, String type, LocalDateTime from, LocalDateTime to) {
//        try {
        Pageable pageable = LocalPagination.getPageable(page, "documentId");
        if (from == null) from = LocalDateTime.of(2020, 1, 1, 0, 0);
        if (to == null) to = LocalDateTime.now();
        if (type == null) return stockDocumentDao.findAllByDateBetween(pageable, from, to);
        if (!type.equals(DOCTYPE.IN.getName()) || type.equals(DOCTYPE.OUT.getName()))
            throw new IllegalArgumentException("Type " + type + " is unacceptable!");
        else return stockDocumentDao.findAllByDateBetweenAndDocumentType(pageable, from, to, type);
//        }catch (Exception ex){
//            System.out.println(ex.getStackTrace().toString());
//            throw new IllegalArgumentException(ex.getMessage());
//        }
    }

    public StockDocument addDocument(StockDocument stockDocument) {
        try {
            if (stockDocument.getDate() == null) stockDocument.setDate(LocalDateTime.now());
            if (stockDocument.getDate().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0))))
                throw new ImpossibleConditionException("It is impossible to add expired date information");
            if (stockDocument.getDate().isAfter(LocalDateTime.now()))
                throw new ImpossibleConditionException("It is impossible to add not today date information");
            stockDocument.setDocumentId(stockDocumentDao.count() + 1);
            return stockDocumentDao.save(stockDocument);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public StockDocument getDocument(long docId) {
        try {
            return stockDocumentDao.findStockDocumentByDocumentId(docId).get();
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    public StockDocument updateDocument(StockDocument stockDocument) {
        return stockDocumentDao.save(stockDocument);
    }

}
