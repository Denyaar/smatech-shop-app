/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/14/24
 * Time: 9:28 AM
 */

package com.smatech.smatech_shop_app.services;

import com.smatech.smatech_shop_app.model.PaymentRequest;
import com.smatech.smatech_shop_app.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PayGateService {

    @Value("${paygate.id}")
    private String apiKey;

    @Value("${paygate.api.endpoint.process}")
    private String apiEndpoint;

    private final RestTemplate restTemplate;

    public PayGateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);

        return restTemplate.postForObject(apiEndpoint, entity, PaymentResponse.class);
    }
}
