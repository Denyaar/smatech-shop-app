/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/7/24
 * Time: 9:54 AM
 */

package com.smatech.smatech_shop_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smatech.smatech_shop_app.security.PasswordValidator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;
    private String name;
    private String email;
    private String address;
    private String mobileNumber;
    private String password;


    public void setPassword(String password) {
        if (!PasswordValidator.validate(password)) {
            throw new IllegalArgumentException("Invalid password format. Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, no whitespace, and be at least 8 characters long.");
        }

        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


}
