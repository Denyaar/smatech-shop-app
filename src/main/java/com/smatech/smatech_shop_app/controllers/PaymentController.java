/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/14/24
 * Time: 9:30 AM
 */

package com.smatech.smatech_shop_app.controllers;

import com.smatech.smatech_shop_app.dtos.PaymentRequest;
import com.smatech.smatech_shop_app.dtos.PaymentResponse;
import com.smatech.smatech_shop_app.model.Order;
import com.smatech.smatech_shop_app.model.Product;
import com.smatech.smatech_shop_app.model.User;
import com.smatech.smatech_shop_app.repository.ProductRepository;
import com.smatech.smatech_shop_app.repository.UserRepository;
import com.smatech.smatech_shop_app.services.EmailSenderService;
import com.smatech.smatech_shop_app.services.OrderService;
import com.smatech.smatech_shop_app.services.PayGateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PayGateService payGateService;
    private final OrderService orderService;

    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    ;

    public PaymentController(PayGateService payGateService, OrderService orderService) {
        this.orderService = orderService;
        this.payGateService = payGateService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Process of payment request: {}", paymentRequest);
        try {
            UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

            Order order = orderService.createOrder(Order.builder()
                    .user(user.get())
                    .products(paymentRequest.getProducts())
                    .status("PENDING_PAYMENT")
                    .orderTotalAmount(paymentRequest.getAmount())
                    .build());

            PaymentResponse response = payGateService.makePayment(paymentRequest);

            if (response.isSuccess()) {

                orderService.updateOrder(order.getId(), Order.builder()
                        .id(order.getId())
                        .user(order.getUser())
                        .products(order.getProducts())
                        .status("PAYMENT_SUCESSFUL")
                        .build());

                paymentRequest.getProducts().forEach(product->{
                   Optional<Product> p1 = productRepository.findById(product.getId());
                   if(p1.isPresent()){
                       Product p = p1.get();
                       p.setQuantity(p.getQuantity()-product.getQuantity());
                       productRepository.save(p);
                   }
                });

                emailSenderService.sendEmail(user.get().getEmail(), "Payment Successful", "Your order has been successfully placed. Order ID: " + order.getId());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new PaymentResponse(false, null, "An error occurred: " + e.getMessage()));
        }
    }


}