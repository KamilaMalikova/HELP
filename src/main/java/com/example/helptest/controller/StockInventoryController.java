package com.example.helptest.controller;

import com.example.helptest.model.StockDocument;
import com.example.helptest.model.StockInventory;
import com.example.helptest.service.StockInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class StockInventoryController {
    @Autowired
    private StockInventoryService stockInventoryService;

    @PostMapping("/{docId}")
    @PreAuthorize("hasAuthority('document:write')")
    public @ResponseBody
    StockDocument addInventories(@PathVariable("docId") long docId, @RequestBody List<StockInventory> stockInventoryList){
        return stockInventoryService.addInventories(docId, stockInventoryList);
    }
}
