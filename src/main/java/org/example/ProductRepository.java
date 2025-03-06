package org.example;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        // Add at least 10 products
        products.add(new Product(1L, "Product 1", 19.99, "Edit 1"));
        products.add(new Product(2L, "Product 2", 29.99, "Description for product 2"));
        products.add(new Product(3L, "Product 3", 39.99, "Description for product 3"));
        products.add(new Product(4L, "Product 4", 49.99, "Description for product 4"));
        products.add(new Product(5L, "Product 5", 59.99, "Description for product 5"));
        products.add(new Product(6L, "Product 6", 69.99, "Description for product 6"));
        products.add(new Product(7L, "Product 7", 79.99, "Description for product 7"));
        products.add(new Product(8L, "Product 8", 89.99, "Description for product 8"));
        products.add(new Product(9L, "Product 9", 99.99, "Description for product 9"));
        products.add(new Product(10L, "Product 10", 109.99, "Description for product 10"));
    }

    public List<Product> findAll() {
        return products;
    }

    public Product findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
