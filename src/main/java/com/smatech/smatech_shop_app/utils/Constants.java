/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/8/24
 * Time: 12:09 AM
 */

package com.smatech.smatech_shop_app.utils;

public class Constants {
        public static final String PHOTO_DIRECTORY_PATH = System.getProperty("user.home") + "/Downloads/uploads/";
        public   static final String X_REQUESTED_WITH = "X-Requested-With";

}



//
//package com.example.payment.controller;
//
//import com.example.payment.dto.PaymentRequest;
//import com.example.payment.dto.PaymentResponse;
//import com.example.payment.service.PayGateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/payments")
//public class PaymentController {
//
//        private final PayGateService payGateService;
//
//        @Autowired
//        public PaymentController(PayGateService payGateService) {
//                this.payGateService = payGateService;
//        }
//
//        @PostMapping("/process")
//        public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
//                try {
//                        PaymentResponse response = payGateService.makePayment(paymentRequest);
//                        if (response.isSuccess()) {
//                                return ResponseEntity.ok(response);
//                        } else {
//                                return ResponseEntity.badRequest().body(response);
//                        }
//                } catch (Exception e) {
//                        return ResponseEntity.internalServerError().body(new PaymentResponse(false, null, "An error occurred: " + e.getMessage()));
//                }
//        }
//}
//
//// src/main/java/com/example/payment/service/PayGateService.java
//
//package com.example.payment.service;
//
//import com.example.payment.dto.PaymentRequest;
//import com.example.payment.dto.PaymentResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class PayGateService {
//
//        @Value("${paygate.api.key}")
//        private String apiKey;
//
//        @Value("${paygate.api.endpoint}")
//        private String apiEndpoint;
//
//        private final RestTemplate restTemplate;
//
//        public PayGateService(RestTemplate restTemplate) {
//                this.restTemplate = restTemplate;
//        }
//
//        public PaymentResponse makePayment(PaymentRequest paymentRequest) {
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//                headers.set("Authorization", "Bearer " + apiKey);
//
//                HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);
//
//                return restTemplate.postForObject(apiEndpoint + "/payments", entity, PaymentResponse.class);
//        }
//}
//
//// src/main/java/com/example/payment/dto/PaymentRequest.java
//
//package com.example.payment.dto;
//
//import java.math.BigDecimal;
//
//public class PaymentRequest {
//        private BigDecimal amount;
//        private String cardNumber;
//        private String expiryMonth;
//        private String expiryYear;
//        private String cvv;
//
//        // Getters and setters
//}
//
//// src/main/java/com/example/payment/dto/PaymentResponse.java
//
//package com.example.payment.dto;
//
//public class PaymentResponse {
//        private boolean success;
//        private String transactionId;
//        private String error;
//
//        public PaymentResponse(boolean success, String transactionId, String error) {
//                this.success = success;
//                this.transactionId = transactionId;
//                this.error = error;
//        }
//
//        // Getters and setters
//}
//
//// src/main/java/com/example/payment/config/RestTemplateConfig.java
//
//package com.example.payment.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class RestTemplateConfig {
//
//        @Bean
//        public RestTemplate restTemplate() {
//                return new RestTemplate();
//        }
//}
//
//// src/main/resources/application.properties
//
//paygate.api.key=your_api_key_here
//paygate.api.endpoint=https://api.paygate.com/v1
//Last edited just now


