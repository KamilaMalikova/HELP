package com.example.helptest.service;

import com.example.helptest.config.LocalPagination;
import com.example.helptest.exception.DuplicateException;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.Category;
import com.example.helptest.model.Units;
import com.example.helptest.repository.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public Category addNewCategory(String categoryName){
        try {
            Category category = new Category(categoryDao.count()+1, categoryName);
            return categoryDao.save(category);
        }catch (Exception ex){
            throw new DuplicateException(ex.getLocalizedMessage());
        }
    }

    public Page<Category> getCategories(int page){
        try {
            return categoryDao.findAll(LocalPagination.getDefaultPageable(page));
        }catch (Exception ex){
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }
    }
    public List<Category> getListCategories(){
        try {
            return categoryDao.findAllByOrderById();
        }catch (Exception ex){
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }
    }

    public Category getCategory(int id){
        try {
            return categoryDao.findCategoryById(id).get();
        }catch (Exception ex){
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }

    public Category updateCategory(int id, String newUnitName){
        try {
            Category category = categoryDao.findCategoryById(id).get();
            category.setCategory(newUnitName);
            return categoryDao.save(category);
        }catch (Exception ex){
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }

    public Category deleteCategory(int id){
        try{
            Category category = categoryDao.findCategoryById(id).get();
            categoryDao.delete(category);
            return category;
        }catch (Exception ex){
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }
}
