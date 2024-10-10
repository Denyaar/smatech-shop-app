/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/7/24
 * Time: 10:32 PM
 */

package com.smatech.smatech_shop_app.services;

import com.smatech.smatech_shop_app.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(String id);
    List<Product> getAllProducts();
    Product updateProduct(String id, Product product);
    Product createProduct(Product product);
    void deleteProduct(String id);

    String uploadImage(String id, MultipartFile file);
}
