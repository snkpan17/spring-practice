package com.example.spring6restmvc.repositories;

import com.example.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void createBeer() {
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("New Beer")
                .build());
        assertThat(beer.getId()).isNotNull();
    }
}