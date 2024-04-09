package com.example.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.spring6restmvc.model.BeerDTO;
import com.example.spring6restmvc.service.BeerService;
import com.example.spring6restmvc.service.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
    private final static String BEER_PATH = "/api/v1/beer";
    private final static String BEER_PATH_ID = BEER_PATH + "/"+"{beerId}";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BeerService beerservice;
    BeerServiceImpl beerServiceImpl;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @BeforeEach
    void setUp() {
       beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void patchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);
        Map<String, Object> map = new HashMap<>();
        map.put("beerName", "New Name");
        mockMvc.perform(patch(BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isNoContent());
        verify(beerservice).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
        assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo(map.get("beerName"));
    }

    @Test
    void deleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);
        mockMvc.perform(delete(BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(beerservice).deleteBeerById(uuidArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
    }

    @Test
    void updateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);
        mockMvc.perform(put(BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());
        verify(beerservice).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void createBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);
        beer.setVersion(null);
        beer.setId(null);
        given(beerservice.createBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.getBeerList().get(1));
        mockMvc.perform(post(BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testBeerList() throws Exception {
        given(beerservice.getBeerList()).willReturn(beerServiceImpl.getBeerList());
        mockMvc.perform(get(BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {
        given(beerservice.getBeerById(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeerById() throws Exception {
        BeerDTO testbeer = beerServiceImpl.getBeerList().get(0);
        given(beerservice.getBeerById(testbeer.getId())).willReturn(Optional.of(testbeer));
        mockMvc.perform(get(BEER_PATH+"/"+testbeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testbeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testbeer.getBeerName())));
        }
}