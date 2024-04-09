package com.example.spring6restmvc.service;

import com.example.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> getBeerList();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO createBeer(BeerDTO beer);

    void updateBeer(UUID beerId, BeerDTO beer);

    void deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
