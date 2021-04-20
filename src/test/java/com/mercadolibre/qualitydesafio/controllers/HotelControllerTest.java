package com.mercadolibre.qualitydesafio.controllers;

import com.mercadolibre.qualitydesafio.dto.HotelDTO;
import com.mercadolibre.qualitydesafio.services.HotelServiceImpl;
import com.mercadolibre.qualitydesafio.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HotelControllerTest {

    private HotelController hotelController;
    private TestUtils testUtils = new TestUtils();

    @MockBean
    private HotelServiceImpl hotelService;

    @Test
    void getHotels() throws Exception {
        List<HotelDTO> hotels = testUtils.loadDatabaseHotelsTest("oneHotel");
        when(hotelService.getHotels(any())).thenReturn(hotels);
        hotelController = new HotelController(hotelService);
        Assertions.assertEquals(hotelController.getHotels(new HashMap<>()).getBody(), hotels);
    }

    @Test
    void makeBooking() {
    }
}