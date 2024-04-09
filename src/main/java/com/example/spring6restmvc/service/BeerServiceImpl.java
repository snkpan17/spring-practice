package com.example.spring6restmvc.service;

import com.example.spring6restmvc.model.BeerDTO;
import com.example.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private final Map<UUID, BeerDTO> beers;

    public BeerServiceImpl() {
        this.beers = new HashMap<>();
        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy New")
                .beerStyle(BeerStyle.LAGER)
                .upc("23433")
                .quantityOnHand(123)
                .version(1)
                .price(new BigDecimal("120.80"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Classic")
                .beerStyle(BeerStyle.ALE)
                .upc("23433")
                .quantityOnHand(123)
                .version(1)
                .price(new BigDecimal("120.80"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy D")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("23433")
                .quantityOnHand(123)
                .version(1)
                .price(new BigDecimal("120.80"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beers.put(beer1.getId(), beer1);
        beers.put(beer2.getId(), beer2);
        beers.put(beer3.getId(), beer3);
    }
    @Override
    public List<BeerDTO> getBeerList(){
       return new ArrayList<>(this.beers.values());
    }
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("getBeerById - Service");
        return Optional.ofNullable(this.beers.get(id));
    }

    @Override
    public BeerDTO createBeer(BeerDTO beer) {
        BeerDTO newBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .version(beer.getVersion())
                .price(beer.getPrice())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now()).build();
        beers.put(newBeer.getId(), newBeer);

        return newBeer;
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTO beer) {
        BeerDTO prev = beers.get(beerId);
        prev.setBeerName(beer.getBeerName());
        prev.setBeerStyle(beer.getBeerStyle());
        prev.setPrice(beer.getPrice());
        prev.setUpc(beer.getUpc());
        prev.setQuantityOnHand(beer.getQuantityOnHand());
        prev.setUpdateDate(LocalDateTime.now());
    }

    @Override
    public void deleteBeerById(UUID beerId) {
        beers.remove(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO prev = beers.get(beerId);
        if(StringUtils.hasText(beer.getBeerName()))
            prev.setBeerName(beer.getBeerName());
        if(beer.getBeerStyle() != null)
            prev.setBeerStyle(beer.getBeerStyle());
        if(beer.getPrice() != null)
            prev.setPrice(beer.getPrice());
        if(StringUtils.hasText(beer.getUpc()))
            prev.setUpc(beer.getUpc());
        if(beer.getQuantityOnHand() != null)
            prev.setQuantityOnHand(beer.getQuantityOnHand());
    }
}
