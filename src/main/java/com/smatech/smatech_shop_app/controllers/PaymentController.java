/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/14/24
 * Time: 9:30 AM
 */

package com.smatech.smatech_shop_app.controllers;

import com.smatech.smatech_shop_app.model.PaymentRequest;
import com.smatech.smatech_shop_app.model.PaymentResponse;
import com.smatech.smatech_shop_app.services.PayGateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PayGateService payGateService;
    public PaymentController(PayGateService payGateService) {
        this.payGateService = payGateService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentResponse response = payGateService.makePayment(paymentRequest);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new PaymentResponse(false, null, "An error occurred: " + e.getMessage()));
        }
    }



}