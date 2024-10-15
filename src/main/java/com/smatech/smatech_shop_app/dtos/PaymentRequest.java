/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/14/24
 * Time: 9:24 AM
 */

package com.smatech.smatech_shop_app.dtos;

import com.smatech.smatech_shop_app.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {
    private Double amount;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
    private List<Product> products;
    private double total;
}
