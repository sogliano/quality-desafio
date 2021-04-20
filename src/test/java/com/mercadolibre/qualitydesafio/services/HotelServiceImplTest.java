package com.mercadolibre.qualitydesafio.services;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.exceptions.InvalidParamQuantityException;
import com.mercadolibre.qualitydesafio.exceptions.InvalidPayloadException;
import com.mercadolibre.qualitydesafio.repositories.HotelRepository;
import com.mercadolibre.qualitydesafio.repositories.HotelRepositoryImpl;
import com.mercadolibre.qualitydesafio.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class HotelServiceImplTest {
    private HotelService hotelService;
    private static TestUtils testUtils = new TestUtils();
    private static List<HotelDTO> oneHotel;
    private static List<HotelDTO> filteredHotels;
    private static List<HotelDTO> allHotels;

    @Mock
    private HotelRepository hotelRepository = new HotelRepositoryImpl();

    @BeforeEach
    void setUp(){
        openMocks(this);
        oneHotel = testUtils.loadDatabaseHotelsTest("oneHotel");
        filteredHotels = testUtils.loadDatabaseHotelsTest("filteredHotels");
        allHotels = testUtils.loadDatabaseHotelsTest("allHotels");
    }

    @Test
    @DisplayName("No parameters GET")
    void getHotelsNoParams() throws Exception {
        when(hotelRepository.getHotels(new HashMap<>())).thenReturn(this.allHotels);
        hotelService = new HotelServiceImpl(hotelRepository);
        Assertions.assertEquals(this.allHotels, hotelService.getHotels(new HashMap<>()));
    }

    @Test
    @DisplayName("Right parameters GET and filter.")
    void getHotelsAllParams() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("dateFrom", "23/02/2021");
        params.put("dateTo","25/02/2021");
        params.put("destination","Puerto Iguazú");
        when(hotelRepository.getHotels(params)).thenReturn(this.filteredHotels);
        hotelService = new HotelServiceImpl(hotelRepository);
        Assertions.assertEquals(this.filteredHotels, hotelService.getHotels(params));
    }

    @Test
    @DisplayName("Wrong parameters GET and exception.")
    void getHotelsException() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("dateFrom", "23/02/2021");
        params.put("dateTo","25/02/2021");
        when(hotelRepository.getHotels(params)).thenReturn(this.filteredHotels);
        hotelService = new HotelServiceImpl(hotelRepository);
        Assertions.assertThrows(InvalidParamQuantityException.class, ()-> hotelService.getHotels(params));
    }

    @Test
    @DisplayName("Payload POST")
    void makeBooking() throws Exception {
        // People List
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        people.add(people1);
        people.add(people2);

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);
        BookingDTO booking = new BookingDTO("10/11/2021", "20/11/2021","Buenos Aires","CH-0002","2","DOUBLE", people, paymentMethod);
        PayloadDTO payload = new PayloadDTO("seba_gonzalez@unmail.com", booking);
        ResponseDTO expectedResponse = new ResponseDTO("seba_gonzalez@unmail.com", 6300, 10, 6930.0, booking, new StatusDTO(200,"The process has been completed successfully."));

        when(hotelRepository.makeBooking(payload)).thenReturn(expectedResponse);
        hotelService = new HotelServiceImpl(hotelRepository);

        // Right payload post. (Return response)
        Assertions.assertEquals(expectedResponse, hotelService.makeBooking(payload));

        // Wrong payload post. (Throw exception)
        payload.setBooking(null);
        Assertions.assertThrows(InvalidPayloadException.class, ()-> hotelService.makeBooking(payload));
    }
}