package com.mercadolibre.qualitydesafio.repositories;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.exceptions.ReservedHotelException;
import com.mercadolibre.qualitydesafio.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelRepositoryImplTest {
    private TestUtils testUtils = new TestUtils();
    private List<HotelDTO> allHotels;
    private List<HotelDTO> filteredHotels;
    private List<HotelDTO> oneHotel;

    @Mock
    private HotelRepository hotelRepository = new HotelRepositoryImpl();

    @BeforeEach
    void setUp(){
        allHotels = testUtils.loadDatabaseHotelsTest("allHotels");
        filteredHotels = testUtils.loadDatabaseHotelsTest("filteredHotels");
        oneHotel = testUtils.loadDatabaseHotelsTest("oneHotel");
    }
    @Test
    void getHotels() throws Exception {
        HashMap<String,String> params = new HashMap<>();
        params.put("dateFrom", "10/02/2021");
        params.put("dateTo", "11/02/2021");
        params.put("destination", "Puerto Iguazú");
        Assertions.assertEquals(filteredHotels, hotelRepository.getHotels(params));
    }

    @Test
    void makeBooking() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        people.add(people1);
        people.add(people2);

        BookingDTO booking = new BookingDTO("10/11/2021", "20/11/2021","Buenos Aires","CH-0002","2","DOUBLE", people, new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6));
        PayloadDTO payload = new PayloadDTO("seba_gonzalez@unmail.com", booking);

        ResponseDTO expectedResponse = new ResponseDTO("seba_gonzalez@unmail.com", 6300, 10, 6930.0, booking, new StatusDTO(200, "The process has been completed successfully."));

        // Right payload
        Assertions.assertEquals(expectedResponse, hotelRepository.makeBooking(payload));

        // Wrong payload (already reserved).
        Assertions.assertThrows(ReservedHotelException.class, () -> hotelRepository.makeBooking(payload));
    }
}