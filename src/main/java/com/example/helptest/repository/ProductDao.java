package com.example.helptest.repository;

import com.example.helptest.model.Category;
import com.example.helptest.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends CrudRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByActiveStatus(Pageable pageable, boolean activeStatus);

    Page<Product> findAllByProductNameContainingAndActiveStatus(Pageable pageable, String productName, boolean activeStatus);

    Page<Product> findAllByCategoryAndActiveStatus(Pageable pageable, Category category, boolean activeStatus);

    Page<Product> findAllByProductNameContainingAndCategoryAndActiveStatus(Pageable pageable, String productName ,Category category, boolean activeStatus);

    Optional<Product> findProductById(long id);

    List<Product> findAllByRestaurantIsTrue();

    List<Product> findAllByProductNameContainingAndRestaurantIsTrue(String productName);

    List<Product> findAllByProductNameContainingAndCategoryAndRestaurantIsTrue(String productName, Category category);

    List<Product> findAllByCategoryAndRestaurantIsTrue(Category category);

    List<Product> findAllByActiveStatus(boolean active);

    List<Product> findAllByCategoryAndActiveStatus(Category category1, boolean active);

    List<Product> findAllByProductNameContainingAndActiveStatus(String productName, boolean active);

    List<Product> findAllByProductNameContainingAndCategoryAndActiveStatus(String productName, Category category1, boolean active);
}
