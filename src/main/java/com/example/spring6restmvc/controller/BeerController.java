package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.model.BeerDTO;
import com.example.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    public final static String BEER_PATH = "/api/v1/beer";
    public final static String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beer){
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeer(@PathVariable UUID beerId){
        beerService.deleteBeerById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beer){
        beerService.updateBeer(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(BEER_PATH)
    public ResponseEntity saveNewBeer(@RequestBody BeerDTO beer){
       BeerDTO savedBeer =  beerService.createBeer(beer);
       HttpHeaders headers = new HttpHeaders();
       headers.add("Location", BEER_PATH+savedBeer.getId());
       return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @GetMapping(BEER_PATH)
    public List<BeerDTO> getBeerList(){
       return beerService.getBeerList();
    }
    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable UUID beerId){
        log.debug("getBeerById(" + beerId + ") - controller");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }
}
