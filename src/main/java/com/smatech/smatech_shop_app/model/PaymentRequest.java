/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/14/24
 * Time: 9:24 AM
 */

package com.smatech.smatech_shop_app.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private Double amount;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
}
