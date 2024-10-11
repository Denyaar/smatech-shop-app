/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/7/24
 * Time: 10:32 PM
 */

package com.smatech.smatech_shop_app.services;

import com.smatech.smatech_shop_app.model.Product;
import com.smatech.smatech_shop_app.repository.ProductRepository;
import com.smatech.smatech_shop_app.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return Optional.of(productRepository.findById(id).orElseThrow());
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        Optional<Product> existingProduct = getProductById(id);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        existingProduct.get().setName(product.getName());
        existingProduct.get().setPrice(product.getPrice());
        existingProduct.get().setQuantity(product.getQuantity());
        existingProduct.get().setImageUrl(product.getImageUrl());
        return productRepository.save(existingProduct.get());

    }

     public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }


    public String uploadImage(String id, MultipartFile file) {
        log.info("Uploading image for product with id: {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        String imageUrl = photoFunction.apply(id, file);

        product.setImageUrl(imageUrl);
        productRepository.save(product);
        return imageUrl;
    }

    private final Function<String, String> fileExtensions = filename -> Optional.of(filename)
            .filter(f -> f.contains("."))
            .map(f -> "." + f.substring(filename.lastIndexOf(".") + 1))
            .orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String fileName = fileExtensions.apply(image.getOriginalFilename());
        try {
            Path path = Paths.get(Constants.PHOTO_DIRECTORY_PATH).toAbsolutePath().normalize();
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.copy(image.getInputStream(), path.resolve(id + fileName), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/product/image/" + id + fileName).toUriString();
        } catch (Exception e) {
            log.error("Error uploading image", e);
            throw new RuntimeException("Error uploading image");
        }
    };
}
