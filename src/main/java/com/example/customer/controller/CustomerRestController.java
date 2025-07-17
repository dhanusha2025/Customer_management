package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getOne(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @PostMapping
    public Customer create(@Valid @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("ID,Name,Email,Phone Number,Location\n");

        for (Customer c : customers) {
            csvBuilder.append(String.format("%d,%s,%s,%s,%s\n",
                    c.getId(),
                    c.getName(),
                    c.getEmail(),
                    c.getPhoneNumber(),
                    c.getLocation()));
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=customers.csv")
                .body(csvBuilder.toString());
    }
}
