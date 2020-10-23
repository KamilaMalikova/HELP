package com.example.helptest.repository;

import com.example.helptest.model.StockDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockDocumentDao extends CrudRepository<StockDocument, Integer> {

    Page<StockDocument> findAllByDateBetweenAndDocumentType(Pageable pageable, LocalDateTime from, LocalDateTime to, String type);

    List<StockDocument> findAllByDateBetweenAndDocumentType(LocalDateTime from, LocalDateTime to, String type);

    Page<StockDocument> findAllByDateBetween(Pageable pageable, LocalDateTime from, LocalDateTime to);

    List<StockDocument> findAllByDateBetween(LocalDateTime from, LocalDateTime to);

    Optional<StockDocument> findStockDocumentByDocumentId(long id);

}
