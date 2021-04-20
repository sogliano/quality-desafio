package com.mercadolibre.qualitydesafio.repositories;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.exceptions.InvalidCodeException;
import com.mercadolibre.qualitydesafio.exceptions.InvalidDestinationException;
import com.mercadolibre.qualitydesafio.exceptions.ReservedHotelException;
import com.mercadolibre.qualitydesafio.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FlightRepositoryImplTest {

    private TestUtils testUtils = new TestUtils();
    private List<FlightDTO> allFlights;
    private List<FlightDTO> filteredFlights;
    private List<FlightDTO> oneFlight;

    @Mock
    private FlightRepository flightRepository = new FlightRepositoryImpl();

    @BeforeEach
    void setUp(){
        allFlights = testUtils.loadDatabaseFlightsTest("allFlights");
        filteredFlights = testUtils.loadDatabaseFlightsTest("filteredFlights");
        oneFlight = testUtils.loadDatabaseFlightsTest("oneFlight");
    }

    @Test
    @DisplayName("Get flights from Repository with no parameters")

    void getFlights() throws Exception {
        List<FlightDTO> x = new ArrayList<>();
        Map<String,String> params = new HashMap<>();
        //when(flightRepository.getFlights(params)).thenReturn(allFlights);
        Assertions.assertEquals(allFlights, flightRepository.getFlights(params));
    }

    @Test
    @DisplayName("Get flights from Repository filtering with parameters.")
    void getFlightsWithParams() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        List<FlightDTO> empty = new ArrayList<>();
        Map<String,String> params = new HashMap<>();
        params.put("destination", "Puerto Iguazú");
        params.put("origin", "Buenos Aires");
        params.put("dateFrom", "10/02/2021");
        params.put("dateTo", "13/02/2021");

        Assertions.assertEquals(filteredFlights, flightRepository.getFlights(params));
    }

    @Test
    @DisplayName("Get flights from Repository filtering with parameters.")
    void makeReservation() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        people.add(people1);
        people.add(people2);
        ReservationDTO reservation = new ReservationDTO("10/11/2021","20/11/2021","Buenos Aires","Puerto Iguazú","BAPI-1235", "2", "ECONOMY", people, new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6));
        PayloadDTO payload = new PayloadDTO("seba_gonzalez@unmail.com", reservation);

        ResponseDTO expectedResponse = new ResponseDTO("seba_gonzalez@unmail.com", 6500, 10, 7150.0, reservation, new StatusDTO(200, "The process has been completed successfully."));

        // Right payload
        Assertions.assertEquals(expectedResponse, flightRepository.makeReservation(payload));

        // Wrong payload (already reserved).
        Assertions.assertThrows(InvalidCodeException.class, () -> flightRepository.makeReservation(payload));
    }
}