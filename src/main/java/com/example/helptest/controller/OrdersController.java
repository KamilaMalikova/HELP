package com.example.helptest.controller;

import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.model.OrderReport;
import com.example.helptest.model.Orders;
import com.example.helptest.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Operation(summary = "Add new order.", description = "Request body parameters: tableId, username")
    @PostMapping
    @PreAuthorize("hasAuthority('order:add')")
    public @ResponseBody
    Orders addOrder(@RequestBody Map<String, String> bodyParams) {
        // must have two params
        int tableId;
        String username;
        if (bodyParams.size() != 2) throw new IllegalArgumentException("Require two params in body");
        tableId = Integer.parseInt(bodyParams.get("tableId"));
        username = bodyParams.get("username");
        return ordersService.addOrder(tableId, username);
    }
    @Operation(summary = "Get page of orders.", description = "Empty request parameters: page of orders. Filter request parameters: {" +
            "\"status\":\"CREATED\"," +
            "\"username\":\"user\"," +
            "\"start\":\"2020-09-11T11:00:00\"," +
            "\"end\":\"2020-10-11T11:42:24.422229\"," +
            "\"tableId\":\"1\"," +
            "}")
    @GetMapping("/{pageId}")
    @PreAuthorize("hasAuthority('order:read')")
    public @ResponseBody
    Page<Orders> getOrders(@PathVariable("pageId") int page,
                           @RequestParam(required = false) Map<String, String> bodyParams) {
        if (bodyParams == null || bodyParams.isEmpty()) return ordersService.getAllOrders(page);
        else {
            return ordersService.filterOrders(page, bodyParams);
        }

    }
    @Operation(summary = "Get order by orderId")
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('order:read')")
    public @ResponseBody
    Orders getOrder(@PathVariable("orderId") long orderId) {
        return ordersService.getOrder(orderId);
    }

    @Operation(summary = "Close order. Find by orderId")
    @PostMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('order:delete')")
    public @ResponseBody
    Orders closeOrder(@PathVariable("orderId") long orderId) {
        return ordersService.closeOrder(orderId);
    }

    @GetMapping("/report")
    public @ResponseBody
    OrderReport createReport(@RequestParam(value = "from", required = false) String from,
                             @RequestParam(value = "to", required = false) String to){
        return ordersService.createReport(LocalDateTime.parse(from), LocalDateTime.parse(to));
    }
}
