package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
@SessionAttributes("shoppingCart")
public class CheckoutController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String checkout(HttpSession session,
                           @ModelAttribute("shoppingCart") ShoppingCart cart,
                           Model model) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");
        if (customer == null) {
            return "redirect:/login";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("total", cart.getTotalCost());
        return "checkout";
    }

    @PostMapping("/confirm")
    public String confirmOrder(HttpSession session,
                               @ModelAttribute("shoppingCart") ShoppingCart cart,
                               SessionStatus sessionStatus) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");
        if (customer == null) {
            return "redirect:/login";
        }

        // Create the order
        Order order = orderService.placeOrder(customer, cart);

        // Clear the cart
        sessionStatus.setComplete();

        return "redirect:/orders";
    }
}