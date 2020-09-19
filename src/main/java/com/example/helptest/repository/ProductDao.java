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

    Page<Product> findAllByProductNameContainingAndActiveStatusAndRestaurant(Pageable pageable, String productName, boolean activeStatus, boolean restaurant);

    Page<Product> findAllByCategoryAndActiveStatusAndRestaurant(Pageable pageable, Category category, boolean activeStatus, boolean restaurant);

    Page<Product> findAllByProductNameContainingAndCategoryAndActiveStatusAndRestaurant(Pageable pageable, String productName, Category category, boolean activeStatus, boolean restaurant);

    Optional<Product> findProductById(long id);

    List<Product> findAllByRestaurantIsTrue();

    List<Product> findAllByProductNameContainingAndRestaurantIsTrue(String productName);

    List<Product> findAllByProductNameContainingAndRestaurantIsTrueAndActiveStatusIsTrue(String productName);

    List<Product> findAllByProductNameContainingAndRestaurantIsTrueOrderByCategoryAsc(String productName);

    List<Product> findAllByProductNameContainingAndCategoryAndRestaurantIsTrue(String productName, Category category);

    List<Product> findAllByProductNameContainingAndCategoryAndRestaurantIsTrueAndActiveStatusIsTrue(String productName, Category category);

    List<Product> findAllByProductNameContainingAndCategoryAndRestaurantIsTrueOrderByCategoryAsc(String productName, Category category);

    List<Product> findAllByCategoryAndRestaurantIsTrue(Category category);

    List<Product> findAllByCategoryAndRestaurantIsTrueAndActiveStatusIsTrue(Category category);

    List<Product> findAllByActiveStatusAndRestaurant(boolean active, boolean restaurant);

    List<Product> findAllByCategoryAndActiveStatusAndRestaurant(Category category1, boolean active, boolean restaurant);

    List<Product> findAllByProductNameContainingAndActiveStatusAndRestaurant(String productName, boolean active, boolean restaurant);

    List<Product> findAllByProductNameContainingAndCategoryAndActiveStatusAndRestaurant(String productName, Category category1, boolean active, boolean restaurant);

    List<Product> findAllByProductNameContainingAndRestaurantIsTrueOrderByCategory(String productName);
}
