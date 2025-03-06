package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;



    // View details of a specific product
    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id);
        if (product == null) {

            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product-details";
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAllVisible());
        return "products";
    }
}
