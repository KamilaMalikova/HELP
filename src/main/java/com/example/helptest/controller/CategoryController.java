package com.example.helptest.controller;

import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.Category;
import com.example.helptest.model.Units;
import com.example.helptest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;


    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('category:read')")
    public @ResponseBody
    Page<Category> getCategories(@PathVariable("pageId") int page){
        return categoryService.getCategories(page);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('category:read')")
    public @ResponseBody
    List<Category> getListCategories(){
        return categoryService.getListCategories();
    }
    @GetMapping("/category/{categoryId}")
    public @ResponseBody
    Category getCategory(@PathVariable("categoryId") int unitId){
        return categoryService.getCategory(unitId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('category:add')")
    public @ResponseBody
    Category addCategory(@RequestBody Map<String, String> bodyParams){
        try {
            return categoryService.addNewCategory(bodyParams.get("category"));
        }catch (Exception ex){
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }
    }

    @PostMapping("/category/{categoryId}")
    @PreAuthorize("hasAuthority('category:write')")
    public @ResponseBody
    Category modifyUnit(@PathVariable("categoryId") int unitId, @RequestBody(required = false) Map<String, String> bodyParams){

        if (bodyParams == null){
            return categoryService.deleteCategory(unitId);
        }else {
            String unitName = bodyParams.get("category");
            return categoryService.updateCategory(unitId, unitName);
        }
    }

}
