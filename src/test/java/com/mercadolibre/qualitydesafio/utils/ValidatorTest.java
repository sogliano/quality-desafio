package com.mercadolibre.qualitydesafio.utils;

import com.mercadolibre.qualitydesafio.dto.*;
import com.mercadolibre.qualitydesafio.exceptions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private final Validator validator = new Validator();
    private PayloadDTO payload = new PayloadDTO();

    @Test
    @DisplayName("Validate Payload")
    void validatePayload() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(12345378L, "Pepeto", "Gowez", "10/11/1984", "pepitxgomez@gmail.com");
        people.add(people1);
        people.add(people2);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);
        BookingDTO booking = new BookingDTO("10/11/2021", "20/11/2021","Buenos Aires","CH-0002","2","DOUBLE", people, paymentMethod);
        payload = new PayloadDTO("seba_gonzalez@unmail.com", booking);

        // Sending correct payload.
        assertEquals(true, validator.validatePayload(payload));

        // Sending wrong payload (null userName).
        payload.setUserName(null);
        assertThrows(InvalidPayloadException.class, () -> validator.validatePayload(payload));

        // Sending wrong payload (wrong userName email).
        payload.setUserName("bademail");
        assertThrows(InvalidEmailException.class, () -> validator.validatePayload(payload));
        payload.setUserName("user@user.com");

        // Sending wrong payload (wrong booking date)
        payload.getBooking().setDateFrom("X");
        assertThrows(InvalidParamValueException.class, () -> validator.validatePayload(payload));
        payload.getBooking().setDateFrom("10/11/2021");

        payload.getBooking().setPeopleAmount("5");
        assertThrows(InvalidPeopleException.class, () -> validator.validatePayload(payload));
    }

    @Test
    @DisplayName("Validate Booking.")
    void validateBooking() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(12345378L, "Pepeto", "Gowez", "10/11/1984", "pepitxgomez@gmail.com");
        people.add(people1);
        people.add(people2);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);

        // Sending correct booking.
        BookingDTO booking = new BookingDTO("10/11/2021", "20/11/2021","Buenos Aires","CH-0002","2","DOUBLE", people, paymentMethod);
        assertEquals(true, validator.validateBooking(booking));

        // Sending wrong booking (dateFrom is more recent than dateTo).
        booking.setDateFrom("21/11/2021");
        assertThrows(InvalidDateException.class, () -> validator.validateBooking(booking));
        booking.setDateFrom("10/11/2021");

        // Sending wrong booking (destination not valid)
        booking.setDestination("A");
        assertThrows(InvalidDestinationException.class, () -> validator.validateBooking(booking));
        booking.setDestination("Buenos Aires");

        // Sending wrong booking (hotelCode not valid)
        booking.setHotelCode("A");
        assertThrows(InvalidCodeException.class, () -> validator.validateBooking(booking));
        booking.setHotelCode("CH-0002");

        // Sending wrong booking (peopleAmount not valid)
        booking.setPeopleAmount("A");
        assertThrows(InvalidPeopleAmountException.class, () -> validator.validateBooking(booking));
        booking.setPeopleAmount("2");

        // Sending wrong booking (roomType not valid)
        booking.setRoomType("A");
        assertThrows(InvalidRoomTypeException.class, () -> validator.validateBooking(booking));
        booking.setRoomType("DOUBLE");

        // Sending wrong booking (wrong People)
        booking.getPeople().get(0).setMail("hola");
        assertThrows(InvalidEmailException.class, () -> validator.validateBooking(booking));
        booking.getPeople().get(0).setMail("pepitogomez@gmail.com");

        // Sending wrong booking (wrong Payment)
        booking.getPaymentMethod().setDues(0);
        assertThrows(InvalidDuesException.class, () -> validator.validateBooking(booking));
    }

    @Test
    @DisplayName("Validate RoomType.")
    void validateRoomType() {
        assertEquals(true, validator.validateRoomType("Double", "2"));
        assertEquals(false, validator.validateRoomType("Single", "3"));
    }

    @Test
    @DisplayName("Validate Email")
    void validateEmail() throws Exception {
        // OK
        assertEquals(true, validator.validateEmail("email", "nsogliano@gmail.com"));
        // Wrong
        assertThrows(InvalidEmailException.class, () -> validator.validateEmail("email", "nsoglianogmail.com"));
    }

    @Test
    @DisplayName("Validate Strings")
    void validateStringParams() throws Exception {
        // Data null
        assertThrows(InvalidParamValueException.class, ()-> validator.validateStringParams("name", null));
        // Name OK
        assertEquals(true, validator.validateStringParams("name", "Nicolas"));
        // Name Wrong
        assertThrows(InvalidPeopleNameException.class, () -> validator.validateStringParams("name", "N"));
        // Origin and Destination OK
        assertEquals(true, validator.validateStringParams("origin", "Buenos Aires"));
        assertEquals(true, validator.validateStringParams("destination", "Buenos Aires"));
        // Origin and Destination Wrong
        assertThrows(InvalidDestinationException.class, () -> validator.validateStringParams("origin","b"));
        assertThrows(InvalidDestinationException.class, () -> validator.validateStringParams("destination","b"));
        // Roomtype OK
        assertEquals(true, validator.validateStringParams("roomType", "Single"));
        // Roomtype Wrong
        assertThrows(InvalidRoomTypeException.class, () -> validator.validateStringParams("roomType","b"));
        // HotelCode OK
        assertEquals(true, validator.validateStringParams("hotelCode", "AAAAAAAA"));
        // HotelCode Wrong
        assertThrows(InvalidCodeException.class, () -> validator.validateStringParams("hotelCode","b"));
        // PeopleAmount and Seats OK
        assertEquals(true, validator.validateStringParams("peopleAmount", "2"));
        assertEquals(true, validator.validateStringParams("seats", "2"));
        // PeopleAmount and Seats Wrong
        assertThrows(InvalidPeopleAmountException.class, () -> validator.validateStringParams("peopleAmount","b"));
        assertThrows(InvalidSeatQuantityException.class, () -> validator.validateStringParams("seats","b"));
        // CardType and SeatType OK
        assertEquals(true, validator.validateStringParams("cardType", "CREDIT"));
        assertEquals(true, validator.validateStringParams("seatType", "ECONOMY"));
        // CardType and SeatType Wrong
        assertThrows(InvalidCardTypeException.class, () -> validator.validateStringParams("cardType","b"));
        assertThrows(InvalidSeatTypeException.class, () -> validator.validateStringParams("seatType","b"));
    }

    @Test
    @DisplayName("Validate Numbers.")
    void isNumeric() {
        assertEquals(false, validator.isNumeric("x"));
        assertEquals(true, validator.isNumeric("3"));
    }

    @Test
    @DisplayName("Validate Keys. Hotels Searching")
    void validateKeysHotelSearch() throws Exception {
        Map<String, String> paramsOk = new HashMap<>();
        paramsOk.put("dateFrom", "X");
        paramsOk.put("dateTo", "X");
        paramsOk.put("destination", "X");
        assertEquals(true, validator.validateKeys(paramsOk, "hotelSearch"));

        Map<String, String> paramsWrong = new HashMap<>();
        paramsOk.put("dateFrom", "X");
        assertThrows(InvalidParamKeyException.class, () -> validator.validateKeys(paramsWrong, "hotelSearch"));
    }

    @Test
    @DisplayName("Validate Keys. Hotels booking.")
    void validateKeysHotelBooking() throws Exception {
        Map<String, String> paramsOk = new HashMap<>();
        paramsOk.put("userName", "X");
        paramsOk.put("booking", "X");
        assertEquals(true, validator.validateKeys(paramsOk, "hotelBooking"));

        Map<String, String> paramsWrong = new HashMap<>();
        paramsWrong.put("userName", "X");
        assertThrows(InvalidParamKeyException.class, () -> validator.validateKeys(paramsWrong, "hotelBooking"));
    }

    @Test
    @DisplayName("Validate Keys when searching hotels.")
    void validateKeysFlightSearch() throws Exception {
        Map<String, String> paramsOk = new HashMap<>();
        paramsOk.put("origin", "X");
        paramsOk.put("destination", "X");
        paramsOk.put("dateFrom", "X");
        paramsOk.put("dateTo", "X");
        assertEquals(true, validator.validateKeys(paramsOk, "flightSearch"));

        Map<String, String> paramsWrong = new HashMap<>();
        paramsWrong.put("origin", "X");
        assertThrows(InvalidParamKeyException.class, () -> validator.validateKeys(paramsWrong, "flightSearch"));
    }

    @Test
    void validateSearchValues() throws Exception {
        // All OK
        Map<String, String> paramsOk = new HashMap<>();
        paramsOk.put("dateFrom", "10/11/2021");
        paramsOk.put("dateTo", "11/11/2021");
        paramsOk.put("destination", "Buenos Aires");
        assertEquals(true, validator.validateSearchValues(paramsOk));

        // Wrong dateFrom
        paramsOk.put("dateFrom", "10/1x/2021");
        assertThrows(InvalidParamValueException.class, () -> validator.validateSearchValues(paramsOk));

        // Wrong destination
        paramsOk.put("dateFrom", "10/11/2021");
        paramsOk.put("destination", "A");
        assertThrows(InvalidParamValueException.class, () -> validator.validateSearchValues(paramsOk));

        // Inverted dates
        paramsOk.put("destination", "Buenos Aires");
        paramsOk.put("dateFrom", "13/11/2021");
        paramsOk.put("dateTo", "11/11/2021");
        assertThrows(InvalidDateException.class, () -> validator.validateSearchValues(paramsOk));
    }

    @Test
    void hotelExists() throws Exception {
        List<HotelDTO> hotels = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        HotelDTO hotel1 = new HotelDTO("AAAA-1111", "HotelUno", "Montevideo", "Double", 3000, LocalDate.parse("01/01/2021", formatter),LocalDate.parse("02/01/2021", formatter),"NO");
        hotels.add(hotel1);

        assertEquals(true, validator.hotelExists("AAAA-1111",hotels));
        assertThrows(InvalidCodeException.class, () -> validator.hotelExists("DDDD-4444",hotels));
    }

    @Test
    void flightExists() throws Exception {
        List<FlightDTO> flights = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        FlightDTO flight1 = new FlightDTO("AAAA-1111","Buenos Aires", "Puerto Iguazú", "ECONOMY", 4000, LocalDate.parse("01/01/2021", formatter),LocalDate.parse("02/01/2021", formatter));
        flights.add(flight1);

        assertEquals(true, validator.flightExists("AAAA-1111",flights));
        assertThrows(InvalidCodeException.class, () -> validator.flightExists("DDDD-4444",flights));

    }

    @Test
    void findHotelByCode() throws Exception {
        List<HotelDTO> hotels = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        HotelDTO hotel1 = new HotelDTO("AAAA-1111", "HotelUno", "Montevideo", "Double", 3000, LocalDate.parse("01/01/2021", formatter),LocalDate.parse("02/01/2021", formatter),"NO");
        hotels.add(hotel1);
        assertEquals(hotel1, validator.findHotelByCode("AAAA-1111", hotels));
        assertThrows(InvalidCodeException.class, ()->validator.findHotelByCode("X", hotels));
    }

    @Test
    void findFlightByCode() throws Exception {
        List<FlightDTO> flights = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        FlightDTO flight1 = new FlightDTO("AAAA-1111","Buenos Aires", "Puerto Iguazú", "ECONOMY", 4000, LocalDate.parse("01/01/2021", formatter),LocalDate.parse("02/01/2021", formatter));
        flights.add(flight1);

        assertEquals(flight1, validator.findFlightByCode("AAAA-1111", flights));
        assertThrows(InvalidCodeException.class, ()->validator.findFlightByCode("X", flights));
    }

    @Test
    void destinationExists() throws Exception {
        List<String> destinations = new ArrayList<>();
        destinations.add("Buenos Aires");
        assertEquals(true, validator.destinationExists("Buenos Aires", destinations));
        assertThrows(InvalidDestinationException.class, () -> validator.destinationExists("Paraguay", destinations));
    }

    @Test
    void originExists() throws Exception {
        List<String> origins = new ArrayList<>();
        origins.add("Buenos Aires");
        assertEquals(true, validator.originExists("Buenos Aires", origins));
        assertThrows(InvalidOriginException.class, () -> validator.originExists("Paraguay", origins));
    }
}