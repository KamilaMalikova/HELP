package com.example.helptest.controller;

import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.Orders;
import com.example.helptest.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping
    @PreAuthorize("hasAuthority('order:add')")
    public @ResponseBody
    Orders addOrder(@RequestBody Map<String, String> bodyParams){
        // must have two params
        int tableId;
        String username;
        if (bodyParams.size() != 2) throw new IllegalArgumentException("Require two params in body");
        tableId = Integer.parseInt(bodyParams.get("tableId"));
        username = bodyParams.get("username");
        return ordersService.addOrder(tableId, username);
    }

    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('order:read')")
    public @ResponseBody
    Page<Orders> getOrders(@PathVariable("pageId") int page,
                           @RequestParam(required = false) Map<String, String> bodyParams){
        if (bodyParams == null || bodyParams.isEmpty()) return ordersService.getAllOrders(page);
        else {
             return ordersService.filterOrders(page,bodyParams);
        }

    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('order:read')")
    public @ResponseBody
    Orders getOrder(@PathVariable("orderId") long orderId){
        return ordersService.getOrder(orderId);
    }

    @PostMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('order:delete')")
    public @ResponseBody
    Orders closeOrder(@PathVariable("orderId") long orderId){
        return ordersService.closeOrder(orderId);
    }
}
