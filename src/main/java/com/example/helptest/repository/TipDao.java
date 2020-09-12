package com.example.helptest.repository;

import com.example.helptest.model.Tip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipDao extends CrudRepository<Tip, Integer> {
    Tip findFirstByOrderById();
}
