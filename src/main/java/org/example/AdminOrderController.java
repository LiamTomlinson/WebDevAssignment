package org.example;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Check if user is admin before allowing access
    private boolean checkAdmin(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");
        return customer != null && customer.isAdmin();
    }

    // List all orders
    @GetMapping
    public String listOrders(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    // View order details
    @GetMapping("/{id}")
    public String viewOrder(HttpSession session, @PathVariable Long id, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        Order order = orderRepository.findById(id);
        if (order == null) {
            return "redirect:/admin/orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/order-details";
    }

    // Update order status
    @PostMapping("/{id}/update-status")
    public String updateOrderStatus(
            HttpSession session,
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }

        orderRepository.updateStatus(id, status);
        return "redirect:/admin/orders/" + id;
    }
}