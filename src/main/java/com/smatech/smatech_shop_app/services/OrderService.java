/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/8/24
 * Time: 12:00 AM
 */

package com.smatech.smatech_shop_app.services;

import com.smatech.smatech_shop_app.model.Order;

public interface OrderService {

    Order createOrder(Order order);
    Order getOrderById(String id);
    Order updateOrder(String id, Order order);
    void deleteOrder(String id);

}
