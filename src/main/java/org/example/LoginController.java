package org.example;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "login"; // login.html template
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Customer customer, HttpSession session, Model model) {
        Customer existing = customerRepository.findByUsername(customer.getUsername());
        if (existing != null && existing.getPassword().equals(customer.getPassword())) {
            session.setAttribute("loggedInCustomer", existing);
            return "redirect:/products"; // redirect to catalogue or home
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInCustomer");
        return "redirect:/products";
    }
}
