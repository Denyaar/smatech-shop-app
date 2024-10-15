/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/16/24
 * Time: 1:45 AM
 */

package com.smatech.smatech_shop_app.dtos;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    private String password;
}
