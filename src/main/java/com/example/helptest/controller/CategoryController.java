package com.example.helptest.controller;

import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.Category;
import com.example.helptest.model.Units;
import com.example.helptest.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(description = "Get pageable category", summary = "Get pageable category")
    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('category:read')")
    public @ResponseBody
    Page<Category> getCategories(@PathVariable("pageId") int page) {
        return categoryService.getCategories(page);
    }

    @Operation(description = "Get list of categories", summary = "Get list of categories")
    @GetMapping
    @PreAuthorize("hasAuthority('category:read')")
    public @ResponseBody
    List<Category> getListCategories() {
        return categoryService.getListCategories();
    }

    @Operation(description = "Get category by id", summary = "Get category by id")
    @GetMapping("/category/{categoryId}")
    public @ResponseBody
    Category getCategory(@PathVariable("categoryId") int unitId) {
        return categoryService.getCategory(unitId);
    }

    @Operation(description = "Add new category.\n To add new: {\"category\":\"category\"}", summary = "Add new category")
    @PostMapping
    @PreAuthorize("hasAuthority('category:add')")
    public @ResponseBody
    Category addCategory(@RequestBody Map<String, String> bodyParams) {
        try {
            return categoryService.addNewCategory(bodyParams.get("category"));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage());
        }
    }

    @Operation(summary = "Update or delete category. As a path variable use categoryId",
            description = "To update a category use: {\"category\":\"category\"}. \n To delete category don't send data in request  body")
    @PostMapping("/category/{categoryId}")
    @PreAuthorize("hasAuthority('category:write')")
    public @ResponseBody
    Category modifyCategory(@PathVariable("categoryId") int unitId, @RequestBody(required = false) Map<String, String> bodyParams) {

        if (bodyParams == null) {
            return categoryService.deleteCategory(unitId);
        } else {
            String unitName = bodyParams.get("category");
            return categoryService.updateCategory(unitId, unitName);
        }
    }

}
