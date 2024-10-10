/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/7/24
 * Time: 11:26 PM
 */

package com.smatech.smatech_shop_app.repository;

import com.smatech.smatech_shop_app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
