package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.model.CustomerDTO;
import com.example.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class CustomerController {
    public final static String CUSTOMER_PATH = "/api/v1/customers";
    public final static String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";
    CustomerService customerService;
    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable UUID customerId, @RequestBody CustomerDTO customer){
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomer(@PathVariable UUID customerId){
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerDTO customer){
        customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.createCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH+"/"+savedCustomer.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getCustomerList(){
       return customerService.getCustomerList();
    }
    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId){
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
