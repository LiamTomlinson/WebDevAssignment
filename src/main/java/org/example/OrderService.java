package org.example;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    public Order placeOrder(Customer customer, ShoppingCart cart) {
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toList());

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setItems(orderItems);
        order.setTotalCost(cart.getTotalCost());
        order.setCustomer(customer);

        customer.getOrders().add(order);
        return order;
    }
}