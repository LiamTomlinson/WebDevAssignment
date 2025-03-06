package org.example;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        if (order.getId() == null) {
            Long maxId = orders.stream()
                    .mapToLong(o -> o.getId() != null ? o.getId() : 0)
                    .max()
                    .orElse(0L);
            order.setId(maxId + 1);
        }
        orders.add(order);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    public Order findById(Long id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateStatus(Long id, OrderStatus status) {
        orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .ifPresent(order -> order.setStatus(status));
    }

    public int count() {
        return orders.size();
    }
}