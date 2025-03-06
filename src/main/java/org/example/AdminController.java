package org.example;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;


    private boolean checkAdmin(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");
        return customer != null && customer.isAdmin();
    }


    @GetMapping
    public String adminDashboard(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("productCount", productRepository.count());
        model.addAttribute("orderCount", orderRepository.count());
        return "admin/dashboard";
    }
}