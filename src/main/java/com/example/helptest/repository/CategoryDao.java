package com.example.helptest.repository;

import com.example.helptest.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryDao extends CrudRepository<Category, Integer> {

    Page<Category> findAll(Pageable pageable);

    List<Category> findAllByOrderById();

    Optional<Category> findCategoryById(int id);

    Optional<Category> findCategoryByCategory(String category);

    Category findDistinctTopByOrderByIdDesc();
}
