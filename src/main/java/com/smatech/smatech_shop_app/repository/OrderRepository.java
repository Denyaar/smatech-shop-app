/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/7/24
 * Time: 11:27 PM
 */

package com.smatech.smatech_shop_app.repository;

import com.smatech.smatech_shop_app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
}
