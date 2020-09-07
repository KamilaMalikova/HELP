package com.example.helptest.controller;

import com.example.helptest.model.Category;
import com.example.helptest.model.StockItemBalance;
import com.example.helptest.service.StockItemBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class StockItemBalanceController {
    @Autowired
    private StockItemBalanceService stockItemBalanceService;

    @PostMapping
    public @ResponseBody
    StockItemBalance addItem(@RequestBody StockItemBalance stockItemBalance){
        return stockItemBalanceService.addItem(stockItemBalance);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    List<StockItemBalance> getAllItems(@RequestParam("id") long id, @RequestParam("category") String category){
        return stockItemBalanceService.filter(new Category(id, category));
    }

    @GetMapping("/item/{id}")
    @PreAuthorize("hasAuthority('product:read')")
    public @ResponseBody
    StockItemBalance getItem(@PathVariable("id") long id){
        return stockItemBalanceService.getItem(id);
    }

    @PostMapping("/item/{id}")
    @PreAuthorize("hasAuthority('product:write')")
    public @ResponseBody
    StockItemBalance updateItem(@PathVariable("id")long id,@RequestBody StockItemBalance stockItemBalance){
        return stockItemBalanceService.updateItem(id, stockItemBalance);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('product:delete')")
    public @ResponseBody
    boolean deleteItem(@PathVariable("id") long id){
        return stockItemBalanceService.deleteItem(id);
    }

}
