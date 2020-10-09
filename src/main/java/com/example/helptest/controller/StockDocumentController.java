package com.example.helptest.controller;

import com.example.helptest.model.StockDocument;
import com.example.helptest.service.StockDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@RestController
@RequestMapping("/documents")
public class StockDocumentController {
    @Autowired
    private StockDocumentService stockDocumentService;
    @Operation(summary = "Get page of stock documents.", description = "Type: (In, Out) . Date format(from - to): 2020-09-11T11:42:24.422229")
    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('document:read')")
    public @ResponseBody
    Page<StockDocument> getDocuments(@PathVariable("pageId") int page,
                                     @RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "from", required = false) String from,
                                     @RequestParam(value = "to", required = false) String to) {

        return stockDocumentService.filter(page, type, (from != null) ? LocalDateTime.parse(from) : null, (to != null) ? LocalDateTime.parse(to) : null);
    }

    @Operation(summary = "Get stock document.")
    @GetMapping("/document/{docId}")
    @PreAuthorize("hasAuthority('document:read')")
    public @ResponseBody
    StockDocument getDocument(@PathVariable("docId") long docId) {
        return stockDocumentService.getDocument(docId);
    }

    @Operation(summary = "Add new stock document.")
    @PostMapping
    @PreAuthorize("hasAuthority('document:add')")
    public @ResponseBody
    StockDocument addDocument(@RequestBody StockDocument stockDocument) {
        return stockDocumentService.addDocument(stockDocument);
    }
}
