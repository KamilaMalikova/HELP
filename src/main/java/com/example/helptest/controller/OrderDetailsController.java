package com.example.helptest.controller;

import com.example.helptest.model.OrderDetail;
import com.example.helptest.model.OrderWrap;
import com.example.helptest.model.Orders;
import com.example.helptest.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    public void getOrderDetails() {

    }

    public void getOrderDetail() {

    }

    @PostMapping("/{orderId}")
    @PreAuthorize("hasAuthority('order:add')")
    public //@ResponseBody
    Orders addOrderDetails(@PathVariable("orderId") long orderId
            , @RequestBody List<OrderWrap> orderWrapList
    ) {
//        System.out.println("-------");
        return orderDetailsService.addOrderDetails(orderId, orderWrapList);
    }

    public void removeOrderDetail() {

    }
}
