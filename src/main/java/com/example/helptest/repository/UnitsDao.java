package com.example.helptest.repository;

import com.example.helptest.model.Units;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitsDao extends CrudRepository<Units, Integer> {

    Page<Units> findAll(Pageable pageable);

    List<Units> findAllByOrderById();

    Optional<Units> findUnitsById(int id);

    Optional<Units> findUnitsByUnitName(String unit);
}
