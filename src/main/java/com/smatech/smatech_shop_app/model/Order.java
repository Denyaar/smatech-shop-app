/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/7/24
 * Time: 10:02 PM
 */

package com.smatech.smatech_shop_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "`orders`")
public class Order {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private List<Product> products;
    private int total;
    private String status;
}
