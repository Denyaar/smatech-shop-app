/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/14/24
 * Time: 9:24 AM
 */

package com.smatech.smatech_shop_app.model;

import lombok.Data;

@Data
public class PaymentResponse {
    private boolean success;
    private String transactionId;
    private String error;

    public PaymentResponse(boolean success, String transactionId, String error) {
        this.success = success;
        this.transactionId = transactionId;
        this.error = error;
    }
}
