package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register"; // returns register.html
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Customer customer) {

        customerRepository.save(customer);
        return "redirect:/login";
    }
}
