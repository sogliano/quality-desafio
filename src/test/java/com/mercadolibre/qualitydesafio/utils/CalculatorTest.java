package com.mercadolibre.qualitydesafio.utils;

import com.mercadolibre.qualitydesafio.dto.BookingDTO;
import com.mercadolibre.qualitydesafio.dto.PaymentMethodDTO;
import com.mercadolibre.qualitydesafio.dto.PeopleDTO;
import com.mercadolibre.qualitydesafio.dto.ReservationDTO;
import com.mercadolibre.qualitydesafio.exceptions.InvalidCodeException;
import com.mercadolibre.qualitydesafio.repositories.FlightRepository;
import com.mercadolibre.qualitydesafio.repositories.FlightRepositoryImpl;
import com.mercadolibre.qualitydesafio.repositories.HotelRepository;
import com.mercadolibre.qualitydesafio.repositories.HotelRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    DatabaseUtils dbUtils = new DatabaseUtils();
    Calculator calculator = new Calculator();
    HotelRepository hotelRepository = new HotelRepositoryImpl();
    FlightRepository flightRepository = new FlightRepositoryImpl();

    @Test
    @DisplayName("Calculator: calculate total with zero interest.")
    void calculateTotalZero(){
        assertEquals(10, calculator.calculateTotal(10,0));
    }

    @Test
    @DisplayName("Calculator: calculate total with non-zero interest.")
    void calculateTotalNonZero(){
        assertEquals(11, calculator.calculateTotal(10,10));
    }

    @Test
    @DisplayName("Calculator: calculate amount (successful)")
    void calculateHotelAmountOk() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        PeopleDTO people3 = new PeopleDTO(12345628L, "Pepitx", "Gomex", "10/11/1992", "pepitogomex@gmail.com");
        PeopleDTO people4 = new PeopleDTO(13345678L, "Fulanitx", "Gomex", "10/11/1993", "fulanitx@gmail.com");
        people.add(people1);
        people.add(people2);
        people.add(people3);
        people.add(people4);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);
        BookingDTO booking = new BookingDTO("10/02/2021", "11/02/2021","Cartagena","BG-0004","4","MULTIPLE", people, paymentMethod);
        HashMap<String,String> params = new HashMap<>();
        assertThrows(InvalidCodeException.class, () -> calculator.calculateHotelAmount(booking,hotelRepository.getHotels(params)));

    }

    @Test
    @DisplayName("Calculator: calculate amount (wrong hotelCode)")
    void calculateHotelAmountWrong() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        people.add(people1);
        people.add(people2);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);
        BookingDTO booking = new BookingDTO("10/11/2021", "20/11/2021","Buenos Aires","ZZ-0000","2","DOUBLE", people, paymentMethod);
        HashMap<String,String> params = new HashMap<>();
        assertThrows(InvalidCodeException.class, ()->calculator.calculateHotelAmount(booking,hotelRepository.getHotels(params)));
    }

    @Test
    @DisplayName("Calculator: calculate amount (successful)")
    void calculateFlightAmountOk() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        PeopleDTO people3 = new PeopleDTO(12345628L, "Pepitx", "Gomex", "10/11/1992", "pepitogomex@gmail.com");
        PeopleDTO people4 = new PeopleDTO(13345678L, "Fulanitx", "Gomex", "10/11/1993", "fulanitx@gmail.com");
        people.add(people1);
        people.add(people2);
        people.add(people3);
        people.add(people4);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);
        ReservationDTO reservation = new ReservationDTO("20/02/2021","24/02/2021","Bogotá", "Medellín","BOME-4442","2","ECONOMY",people,paymentMethod);
        HashMap<String,String> params = new HashMap<>();
        assertEquals(11000, calculator.calculateFlightAmount(reservation,flightRepository.getFlights(params)));
    }

    @Test
    @DisplayName("Calculator: calculate amount (wrong flightCode)")
    void calculateFlightAmountWrong() throws Exception {
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO(12345678L, "Pepito", "Gomez", "10/11/1982", "pepitogomez@gmail.com");
        PeopleDTO people2 = new PeopleDTO(13345678L, "Fulanito", "Gomez", "10/11/1983", "fulanito@gmail.com");
        people.add(people1);
        people.add(people2);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", 6);
        ReservationDTO reservation = new ReservationDTO("10/11/2021","20/11/2021","Buenos Aires", "Puerto Iguazú","XXXX-XXXX","2","ECONOMY",people,paymentMethod);
        HashMap<String,String> params = new HashMap<>();
        assertThrows(InvalidCodeException.class, ()-> calculator.calculateFlightAmount(reservation,flightRepository.getFlights(params)));
    }

    @Test
    @DisplayName("Calculate: calculate interest")
    void calculateInterest(){
        assertEquals(0, calculator.calculateInterest(1));
        assertEquals(5, calculator.calculateInterest(3));
        assertEquals(10, calculator.calculateInterest(6));
        assertEquals(15, calculator.calculateInterest(9));
        assertEquals(15, calculator.calculateInterest(12));
        assertEquals(null, calculator.calculateInterest(15));
    }
}