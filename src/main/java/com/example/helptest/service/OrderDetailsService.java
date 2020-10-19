package com.example.helptest.service;

import com.example.helptest.exception.BigValueException;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.exception.ImpossibleConditionException;
import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.OrderDetail;
import com.example.helptest.model.OrderWrap;
import com.example.helptest.model.Orders;
import com.example.helptest.model.Product;
import com.example.helptest.repository.OrderDetailsDao;
import com.example.helptest.repository.OrdersDao;
import com.example.helptest.repository.ProductDao;
import org.hibernate.boot.jaxb.hbm.internal.CacheAccessTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsDao orderDetailsDao;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrdersService ordersService;

    public void getOrderDetails(long orderId) {

    }

    public Orders addOrderDetails(long orderId, List<OrderWrap> orderWrapList) {
        try {
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (OrderWrap orderWrap : orderWrapList) {

                    OrderDetail orderDetail = this.getOrderDetail(orderWrap);

                    Product product = orderDetail.getProduct();
                    product.setInStockQty(product.getInStockQty() - orderDetail.getQuantity());
                    productDao.save(product);

                    orderDetailsDao.save(orderDetail);

                    orderDetailList.add(orderDetail);
            }
            //orderDetailsDao.saveAll(orderDetailList);
            return ordersService.getOrder(orderId);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    private boolean checkOrderDetail(OrderWrap orderWrap) {
        try {
            Product product = productDao.findProductById(orderWrap.getProductId()).get();
            Orders order = ordersDao.findOrdersByOrderId(orderWrap.getOrderId()).get();
            if (order.getOrderStatus().equals("CLOSED"))
                throw new ImpossibleConditionException("The order is closed. It's impossible to add new item to order");

            List<OrderDetail> orderDetails = orderDetailsDao.findAllByOrderAndAndProduct(order, product);

            if (orderDetails == null || orderDetails.isEmpty()) return true; // can add to list
            else {
                double dbQty = 0.0;
                for (OrderDetail orderDetail : orderDetails) {
                    dbQty += orderDetail.getQuantity();
                }

                double resultQty = this.getQtyDifference(dbQty, orderWrap.getQty());

                if (resultQty < 0.0)
                    throw new IllegalArgumentException("You ordered less of " + product.getProductName() + " then want to cancel");
                else return true;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    private double getQtyDifference(double a, double b) {
        return a - b;
    }


    private OrderDetail getOrderDetail(OrderWrap orderWrap) {
        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(orderDetailsDao.count() + 1);
            orderDetail.setOrder(ordersDao.findOrdersByOrderId(orderWrap.getOrderId()).get());
            orderDetail.setProduct(productDao.findProductById(orderWrap.getProductId()).get());
            orderDetail.setQuantity(orderWrap.getQty());
            return orderDetail;
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }


    public void removeOrderDetail(long orderId, long orderDetailId) {

    }
}
