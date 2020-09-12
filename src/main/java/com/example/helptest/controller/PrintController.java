package com.example.helptest.controller;

import com.example.helptest.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//usb://Printer/POS-80|Printer POS-80
@RestController
@RequestMapping("/print")
public class PrintController {
    @Autowired
    private PrinterService printerService;

    @GetMapping("/{orderId}")
    public void print(@PathVariable("orderId") long orderId){
        printerService.print(orderId);
    }
}
