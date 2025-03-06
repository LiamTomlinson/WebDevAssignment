package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@SessionAttributes("shoppingCart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;


    @ModelAttribute("shoppingCart")
    public ShoppingCart shoppingCart() {
        return new ShoppingCart();
    }

    // Display the shopping cart
    @GetMapping
    public String viewCart(@ModelAttribute("shoppingCart") ShoppingCart shoppingCart, Model model) {
        model.addAttribute("cart", shoppingCart);
        return "cart";
    }

    // Add an item to the cart (expects productId and an optional quantity, default is 1)
    @PostMapping("/add")
    public String addItem(@RequestParam Long productId,
                          @RequestParam(defaultValue = "1") int quantity,
                          @ModelAttribute("shoppingCart") ShoppingCart shoppingCart) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            shoppingCart.addItem(product, quantity);
        }
        return "redirect:/cart";
    }

    // Update the quantity of an item in the cart
    @PostMapping("/update")
    public String updateItem(@RequestParam Long productId,
                             @RequestParam int quantity,
                             @ModelAttribute("shoppingCart") ShoppingCart shoppingCart) {
        shoppingCart.updateItem(productId, quantity);
        return "redirect:/cart";
    }

    // Remove an item from the cart
    @PostMapping("/remove")
    public String removeItem(@RequestParam Long productId,
                             @ModelAttribute("shoppingCart") ShoppingCart shoppingCart) {
        shoppingCart.removeItem(productId);
        return "redirect:/cart";
    }
}
