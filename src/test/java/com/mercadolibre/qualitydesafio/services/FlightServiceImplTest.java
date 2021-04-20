package com.mercadolibre.qualitydesafio.services;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.exceptions.InvalidParamQuantityException;
import com.mercadolibre.qualitydesafio.exceptions.InvalidPayloadException;
import com.mercadolibre.qualitydesafio.repositories.FlightRepository;
import com.mercadolibre.qualitydesafio.repositories.FlightRepositoryImpl;
import com.mercadolibre.qualitydesafio.repositories.HotelRepository;
import com.mercadolibre.qualitydesafio.repositories.HotelRepositoryImpl;
import com.mercadolibre.qualitydesafio.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FlightServiceImplTest {
    private FlightService flightService;
    private static TestUtils testUtils = new TestUtils();
    private static List<FlightDTO> oneFlight;
    private static List<FlightDTO> filteredFlights;
    private static List<FlightDTO> allFlights;

    @Mock
    private FlightRepository flightRepository = new FlightRepositoryImpl();

    @BeforeEach
    void setUp(){
        openMocks(this);
        oneFlight = testUtils.loadDatabaseFlightsTest("oneFlight");
        filteredFlights = testUtils.loadDatabaseFlightsTest("filteredFlights");
        allFlights = testUtils.loadDatabaseFlightsTest("allFlights");
    }

    @Test
    @DisplayName("No parameters GET")
    void getFlightsNoParams() throws Exception {
        HashMap<String,String> params = new HashMap<>();
        when(flightRepository.getFlights(params)).thenReturn(allFlights);
        flightService = new FlightServiceImpl(flightRepository);
        Assertions.assertEquals(allFlights, flightService.getFlights(params));
    }

    @Test
    @DisplayName("Right parameters GET and filter.")
    void getFlightsAllParams() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("dateFrom", "10/02/2021");
        params.put("dateTo","13/02/2021");
        params.put("destination","Puerto Iguazú");
        params.put("origin", "Buenos Aires");
        when(flightRepository.getFlights(params)).thenReturn(filteredFlights);
        flightService = new FlightServiceImpl(flightRepository);

        // Right path.
        Assertions.assertEquals(filteredFlights, flightService.getFlights(params));

        // Throw exception.
        params.remove("origin");
        Assertions.assertThrows(InvalidParamQuantityException.class, () -> flightService.getFlights(params));
    }

    @Test
    @DisplayName("Right payload POST and return response.")
    void makeReservation() throws Exception {
        // People List
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        people.add(people1);
        people.add(people2);

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);
        ReservationDTO reservation = new ReservationDTO("10/11/2021","20/11/2021","Buenos Aires","Puerto Iguazú","FFFF-0002", "2", "ECONOMY", people, paymentMethod);
        PayloadDTO payload = new PayloadDTO("seba_gonzalez@unmail.com", reservation);
        ResponseDTO expectedResponse = new ResponseDTO("seba_gonzalez@unmail.com", 6300, 10, 6930.0, reservation, new StatusDTO(200,"The process has been completed successfully."));
        when(flightRepository.makeReservation(payload)).thenReturn(expectedResponse);
        flightService = new FlightServiceImpl(flightRepository);

        // Right path.
        Assertions.assertEquals(expectedResponse, flightService.makeReservation(payload));
        // Throw exception.
        payload.setFlightReservation(null);
        Assertions.assertThrows(InvalidPayloadException.class, () -> flightService.makeReservation(payload));

    }
}