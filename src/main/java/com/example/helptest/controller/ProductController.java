package com.example.helptest.controller;

import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.Product;
import com.example.helptest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    Page<Product> getProducts(@PathVariable("pageId") int page,
                              @RequestParam(value = "productName", required = false) String productName,
                              @RequestParam(value = "category", required = false) Integer category,
                              @RequestParam(value = "active", required = false, defaultValue = "1") boolean active){
        if (productName == null && category == null){
          return productService.getProducts(page, active);
        }else {
          return productService.getProducts(page, productName, category, active);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    List<Product> getProducts(@RequestParam(value = "productName", required = false) String productName,
                              @RequestParam(value = "category", required = false) Integer category,
                              @RequestParam(value = "active", required = false, defaultValue = "1") boolean active){
        if (productName == null && category == null){
            return productService.getProducts(active);
        }else {
            return productService.getProducts(productName, category, active);
        }
    }

    @GetMapping("/product/{productCode}")
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    Product getProduct(@PathVariable("productCode") long productCode){
        return productService.getProduct(productCode);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('product:add')")
    public @ResponseBody
    Product addProduct(@RequestBody Map<String, String> bodyParams){
        if (bodyParams.isEmpty()) throw new IllegalArgumentException("The body is empty");
        if (!bodyParams.containsKey("category") || !bodyParams.containsKey("productName")
                || !bodyParams.containsKey("unit")
                || !bodyParams.containsKey("restaurant")) throw new IllegalArgumentException("Not all parameters are provided");
        if (!bodyParams.containsKey("inStockQty")) bodyParams.put("inStockQty", "0.00");
        return productService.addProduct(bodyParams);
    }

    @PostMapping("/product/{productCode}")
    @PreAuthorize("hasAuthority('product:write')")
    public @ResponseBody
    Product modifyProduct(@PathVariable("productCode") long productCode, @RequestBody Map<String, String> bodyParams){
        return productService.updateProduct(productCode, bodyParams);
    }
}
