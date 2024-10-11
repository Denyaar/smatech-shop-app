/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/8/24
 * Time: 12:01 AM
 */

package com.smatech.smatech_shop_app.services;

import com.smatech.smatech_shop_app.model.Order;
import com.smatech.smatech_shop_app.model.Product;
import com.smatech.smatech_shop_app.model.User;
import com.smatech.smatech_shop_app.repository.OrderRepository;
import com.smatech.smatech_shop_app.repository.ProductRepository;
import com.smatech.smatech_shop_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    public Order createOrder(Order orderRequest) {
        User user = userRepository.findById(orderRequest.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Product> orderItems = orderRequest.getProducts().stream()
                .map(item -> productRepository.findById(item.getId())
                         .orElseThrow(() -> new RuntimeException("Product not found")))
                .collect(Collectors.toList());

        Double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        Order order = new Order(null,user , orderItems,totalAmount, "PENDING");
        return orderRepository.save(order);
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order updateOrder(String id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setProducts(order.getProducts());
        existingOrder.setOrderTotalAmount(order.getOrderTotalAmount());
        existingOrder.setStatus(order.getStatus());
        return orderRepository.save(existingOrder);
    }

    @Override
    public List<Order> getUserOrders(String userId) {
     return  orderRepository.findAll()
               .parallelStream()
               .filter(order -> order.getUser().getId().equals(userId))
               .toList();
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
