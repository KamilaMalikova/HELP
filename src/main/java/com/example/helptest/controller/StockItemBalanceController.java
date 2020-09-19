package com.example.helptest.controller;

import com.example.helptest.model.Category;
import com.example.helptest.model.StockItemBalance;
import com.example.helptest.service.StockItemBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class StockItemBalanceController {
    @Autowired
    private StockItemBalanceService stockItemBalanceService;

    @Operation(summary = "Add new item to stock item balance.")
    @PostMapping
    @PreAuthorize("hasAuthority('product:add')")
    public @ResponseBody
    StockItemBalance addItem(@RequestBody StockItemBalance stockItemBalance) {
        return stockItemBalanceService.addItem(stockItemBalance);
    }

    @Operation(summary = "Get list of all items in stock by category.")
    @GetMapping
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    List<StockItemBalance> getAllItems(@RequestParam("id") long id, @RequestParam("category") String category) {
        return stockItemBalanceService.filter(new Category(id, category));
    }

    @Operation(summary = "Get item in stock balance by id.")
    @GetMapping("/item/{id}")
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    StockItemBalance getItem(@PathVariable("id") long id) {
        return stockItemBalanceService.getItem(id);
    }

    @Operation(summary = "Update item in stock balance.")
    @PostMapping("/item/{id}")
    @PreAuthorize("hasAuthority('product:write')")
    public @ResponseBody
    StockItemBalance updateItem(@PathVariable("id") long id, @RequestBody StockItemBalance stockItemBalance) {
        return stockItemBalanceService.updateItem(id, stockItemBalance);
    }

    @Operation(summary = "Delete item from stock balance.")
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('product:delete')")
    public @ResponseBody
    boolean deleteItem(@PathVariable("id") long id) {
        return stockItemBalanceService.deleteItem(id);
    }

}
