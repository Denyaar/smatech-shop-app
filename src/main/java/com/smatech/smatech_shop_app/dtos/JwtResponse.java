/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/11/24
 * Time: 12:07 AM
 */

package com.smatech.smatech_shop_app.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String name;
    private String email;


    public JwtResponse(String accessToken, String id, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.name = username;
        this.email = email;
    }
}
