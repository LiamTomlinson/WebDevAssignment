package org.example;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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



    public Product findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }



    public void save(Product product) {
        // Assign a new ID if not set
        if (product.getId() == null) {
            long maxId = products.stream()
                    .mapToLong(p -> p.getId())
                    .max()
                    .orElse(0);
            product.setId(maxId + 1);
            products.add(product);
        }
    }

    public void update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                // Update the product but keep the original name
                Product existing = products.get(i);
                existing.setPrice(product.getPrice());
                existing.setDescription(product.getDescription());
                existing.setVisible(product.isVisible());
                break;
            }
        }
    }

    public void toggleVisibility(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setVisible(!product.isVisible());
                break;
            }
        }
    }

    public int count() {
        return products.size();
    }

    // method to consider visibility for regular users
    public List<Product> findAll() {
        return products;
    }

    // method to get only visible products for non-admin users
    public List<Product> findAllVisible() {
        return products.stream()
                .filter(Product::isVisible)
                .collect(Collectors.toList());
    }
}
