package com.example.spring6restmvc.service;

import com.example.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    Map<UUID, CustomerDTO> customers;

    public CustomerServiceImpl() {
        this.customers = new HashMap<>();
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Sounak")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Shyam")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Ronny")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        this.customers.put(customer1.getId(), customer1);
        this.customers.put(customer2.getId(), customer2);
        this.customers.put(customer3.getId(), customer3);
    }

    @Override
    public List<CustomerDTO> getCustomerList() {
        return new ArrayList<>(this.customers.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(this.customers.get(id));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
       CustomerDTO newCustomer = CustomerDTO.builder()
               .id(UUID.randomUUID())
               .name(customer.getName())
               .version(customer.getVersion())
               .createdDate(LocalDateTime.now())
               .lastModifiedDate(LocalDateTime.now())
               .build();
       customers.put(newCustomer.getId(), newCustomer);
       return newCustomer;
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDTO customer) {
        CustomerDTO prev = customers.get(customerId);
        prev.setName(customer.getName());
        prev.setLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO prev = customers.get(customerId);
        if(StringUtils.hasText(customer.getName()))
           prev.setName(customer.getName());
    }
}
