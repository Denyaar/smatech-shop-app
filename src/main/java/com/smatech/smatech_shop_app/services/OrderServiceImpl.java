/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/8/24
 * Time: 12:01 AM
 */

package com.smatech.smatech_shop_app.services;

import com.smatech.smatech_shop_app.model.Order;
import com.smatech.smatech_shop_app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order updateOrder(String id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setProducts(order.getProducts());
        existingOrder.setTotal(order.getTotal());
        existingOrder.setStatus(order.getStatus());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
