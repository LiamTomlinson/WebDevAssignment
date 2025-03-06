package org.example;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;

    // Check if user is admin before allowing access
    private boolean checkAdmin(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");
        return customer != null && customer.isAdmin();
    }

    // List all products for admin
    @GetMapping
    public String listProducts(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("products", productRepository.findAll());
        return "admin/products";
    }

    // Form to add a new product
    @GetMapping("/add")
    public String showAddProductForm(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("product", new Product());
        return "admin/product-form";
    }

    // Save new product
    @PostMapping("/add")
    public String addProduct(HttpSession session, @ModelAttribute Product product) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Form to edit existing product
    @GetMapping("/edit/{id}")
    public String showEditProductForm(HttpSession session, @PathVariable Long id, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        Product product = productRepository.findById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }

        model.addAttribute("product", product);
        return "admin/product-form";
    }

    // Update existing product
    @PostMapping("/edit/{id}")
    public String updateProduct(HttpSession session, @PathVariable Long id, @ModelAttribute Product product) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        Product existingProduct = productRepository.findById(id);
        if (existingProduct != null) {

            product.setId(id);
            product.setName(existingProduct.getName());
            productRepository.update(product);
        }

        return "redirect:/admin/products";
    }

    // Toggle product visibility
    @PostMapping("/toggle-visibility/{id}")
    public String toggleProductVisibility(HttpSession session, @PathVariable Long id) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        productRepository.toggleVisibility(id);
        return "redirect:/admin/products";
    }
}