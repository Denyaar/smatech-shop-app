/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/11/24
 * Time: 4:37 PM
 */

package com.smatech.smatech_shop_app.controllers;

import com.smatech.smatech_shop_app.model.Order;
import com.smatech.smatech_shop_app.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable String userId) {
        return orderService.getUserOrders(userId);
    }
}
