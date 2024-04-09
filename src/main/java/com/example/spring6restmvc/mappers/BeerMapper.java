package com.example.spring6restmvc.mappers;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    BeerDTO beerTobeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDTO beerDto);
}
