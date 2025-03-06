package org.example;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerRepository {

    // username -> Customer
    private Map<String, Customer> customers = new HashMap<>();
    private Long idCounter = 1L;

    public Customer save(Customer customer) {
        // Assign an ID if not already set
        if (customer.getId() == null) {
            customer.setId(idCounter++);
        }
        customers.put(customer.getUsername(), customer);
        return customer;
    }

    public Customer findByUsername(String username) {
        return customers.get(username);
    }
}
