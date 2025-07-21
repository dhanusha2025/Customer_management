package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerFormController {

    private final CustomerService customerService;

    public CustomerFormController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "index"; // Loads templates/index.html
    }

    @PostMapping("/customers")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/";
    }
}
