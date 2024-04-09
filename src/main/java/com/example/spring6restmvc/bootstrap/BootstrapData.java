package com.example.spring6restmvc.bootstrap;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.entities.Customer;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.repositories.BeerRepository;
import com.example.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
       loadBeerData();
       loadCustomerData();
    }

    private void loadBeerData() {
        if(beerRepository.count() > 0)
            return;
        Beer beer1 = Beer.builder()
                .beerName("Galaxy New")
                .beerStyle(BeerStyle.LAGER)
                .upc("23433")
                .quantityOnHand(123)
                .price(new BigDecimal("120.80"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Galaxy Classic")
                .beerStyle(BeerStyle.ALE)
                .upc("23433")
                .quantityOnHand(123)
                .price(new BigDecimal("120.80"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Galaxy D")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("23433")
                .quantityOnHand(123)
                .price(new BigDecimal("120.80"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
    }

    private void loadCustomerData() {
        if(customerRepository.count() > 0)
            return;
        Customer customer1 = Customer.builder()
                .name("Sounak")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .name("Shyam")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        Customer customer3 = Customer.builder()
                .name("Ronny")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
    }
}
