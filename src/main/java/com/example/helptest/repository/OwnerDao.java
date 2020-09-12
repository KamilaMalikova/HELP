package com.example.helptest.repository;

import com.example.helptest.model.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerDao extends CrudRepository<Owner, Integer> {
    Owner findFirstByOrderById();
}
