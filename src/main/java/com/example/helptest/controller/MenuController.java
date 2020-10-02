package com.example.helptest.controller;

import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.Product;
import com.example.helptest.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private ProductService productService;
    @Operation(summary = "Get list of menu items", description = "To filter add request parameters: productName and category.")
    @GetMapping
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    List<Product> getProducts(@RequestParam(value = "productName", required = false) String productName,
                              @RequestParam(value = "category", required = false) Integer category) {
        if (productName == null && category == null) {
            return productService.getMenu();
        } else {
            return productService.getMenu(productName, category);
        }
    }

    @Operation(summary = "Get list of menu items sorted by category")
    @GetMapping("/order")
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    Map<String, List<Product>> getProducts() {
        return productService.getMenuOrder();
    }



    @Operation(summary = "Get menu item by product code.")
    @GetMapping("/{productCode}")
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    Product getProduct(@PathVariable("productCode") long productCode) {
        return productService.getProduct(productCode);
    }

}

