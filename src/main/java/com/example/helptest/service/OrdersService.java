package com.example.helptest.service;

import com.example.helptest.config.LocalPagination;
import com.example.helptest.exception.IllegalArgumentException;
import com.example.helptest.exception.NotFoundException;
import com.example.helptest.model.*;
import com.example.helptest.repository.EatingPlaceDao;
import com.example.helptest.repository.OrdersDao;
import com.example.helptest.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private EatingPlaceDao eatingPlaceDao;
    @Autowired
    private UserDAO userDAO;

    public Page<Orders> filterOrders(int page, Map<String, String> filterParams) {

        Pageable pageable = LocalPagination.getPageable(page, "orderId");

        String status = ""; //CREATED - CLOSED // not required
        String username = ""; // not required
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0); // not required
        LocalDateTime end = LocalDateTime.now(); // not required
        int tableId = 0;

        if (filterParams.containsKey("status")) status = filterParams.get("status");
        if (filterParams.containsKey("username")) username = filterParams.get("username");
        if (filterParams.containsKey("start")) start = LocalDateTime.parse(filterParams.get("start"));
        if (filterParams.containsKey("end")) end = LocalDateTime.parse(filterParams.get("end"));
        if (filterParams.containsKey("tableId")) tableId = Integer.parseInt(filterParams.get("tableId"));

        if (tableId == 0) {
            if (status.isBlank() || status.isEmpty()) {
                if (username.isEmpty() || username.isBlank()) {
                    return ordersDao.findAllByCreatedAtBetween(pageable, start, end);
                } else {
                    return ordersDao.findAllByCreatedAtBetweenAndWaiterUsername(pageable, start, end, username);
                }
            } else {
                if (username.isEmpty() || username.isBlank()) {
                    return ordersDao.findAllByCreatedAtBetweenAndOrderStatus(pageable, start, end, status);
                } else {
                    return ordersDao.findAllByCreatedAtBetweenAndWaiterUsernameAndOrderStatus(pageable, start, end, username, status);
                }
            }
        } else {
            if (status.isBlank() || status.isEmpty()) {
                if (username.isEmpty() || username.isBlank()) {
                    return ordersDao.findAllByCreatedAtBetweenAndTableId(pageable, start, end, tableId);
                } else {
                    return ordersDao.findAllByCreatedAtBetweenAndWaiterUsernameAndTableId(pageable, start, end, username, tableId);
                }
            } else {
                if (username.isEmpty() || username.isBlank()) {
                    return ordersDao.findAllByCreatedAtBetweenAndOrderStatusAndTableId(pageable, start, end, status, tableId);
                } else {
                    return ordersDao.findAllByCreatedAtBetweenAndWaiterUsernameAndOrderStatusAndTableId(pageable, start, end, username, status, tableId);
                }
            }
        }

    }

    public Page<Orders> getAllOrders(int page) {
        return ordersDao.findAll(LocalPagination.getPageable(page, "orderId"));
    }

    public Orders getOrder(long orderId) {
        try {
            Orders order = ordersDao.findOrdersByOrderId(orderId).get();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                ProductForOrder productForOrder = new ProductForOrder(orderDetail.getProduct().getId(),
                        orderDetail.getProduct().getProductName(),
                        orderDetail.getProduct().getCategory(),
                        orderDetail.getProduct().getUnit(),
                        orderDetail.getProduct().getCost());
                orderDetail.setProduct(productForOrder);
            }
            return order;
        } catch (Exception ex) {
            throw new NotFoundException("Order is not present");
        }

    }

    public Orders addOrder(int tableId, String waiterUsername) {
        Optional<EatingPlace> eatingPlace = eatingPlaceDao.findById(tableId);
        if (eatingPlace.isEmpty()) throw new IllegalArgumentException("Table not found exception!");
        if (!eatingPlace.get().isActive() || !eatingPlace.get().isReserved())
            throw new IllegalArgumentException("Table is not active or not reserved!");
        if (!eatingPlace.get().getWaiterUsername().equals(waiterUsername))
            throw new IllegalArgumentException("Another waiter serves the table!");

        Optional<Orders> openedOrder = ordersDao.findOrdersByOrderStatusAndTableId(OrderStatus.CREATED.name(), tableId);
        if (!openedOrder.isEmpty()) throw new IllegalArgumentException("Table is reserved");

        Orders order = new Orders(tableId, waiterUsername);
        order.setOrderId(ordersDao.count() + 1);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED.name());
        order.setWaiterName(eatingPlace.get().getWaiterName());
        return ordersDao.save(order);
    }

    public Orders closeOrder(long orderId) {
        try {
            Orders order = ordersDao.findOrdersByOrderId(orderId).get();
            order.setOrderStatus(OrderStatus.CLOSED.name());

            EatingPlace eatingPlace = eatingPlaceDao.findEatingPlaceById(order.getTableId()).get();
            eatingPlace.setWaiterName("free");
            eatingPlace.setReserved(false);
            eatingPlace.setWaiterUsername("null");

            eatingPlaceDao.save(eatingPlace);

            return ordersDao.save(order);
        } catch (Exception ex) {
            throw new NotFoundException(ex.getLocalizedMessage());
        }
    }
}
