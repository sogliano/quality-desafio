package com.mercadolibre.qualitydesafio.utils;

import com.mercadolibre.qualitydesafio.dto.BookingDTO;
import com.mercadolibre.qualitydesafio.dto.FlightDTO;
import com.mercadolibre.qualitydesafio.dto.HotelDTO;
import com.mercadolibre.qualitydesafio.dto.ReservationDTO;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class Calculator {

    private Validator validator = new Validator();

    public Double calculateTotal(Integer value, Integer interest){
        if(interest == 0){
            return (double)value;
        } else {
            return (double)value + value * interest / 100;
        }
    }

    // Obtain Hotel price by booking's hotelCode.
    public Integer calculateHotelAmount(BookingDTO booking, List<HotelDTO> hotels) throws Exception{
        Integer amount = 0;
        if(validator.hotelExists(booking.getHotelCode(), hotels)){
            HotelDTO aux = validator.findHotelByCode(booking.getHotelCode(), hotels);
            amount = aux.getPrice();
        }
        return amount;
    }

    // Obtain Flight price by flight reservation's flightNumber.
    public Integer calculateFlightAmount(ReservationDTO reservation, List<FlightDTO> flights) throws Exception{
        Integer amount = 0;
        if(validator.flightExists(reservation.getFlightNumber(), flights)){
            FlightDTO aux = validator.findFlightByCode(reservation.getFlightNumber(), flights);
            amount = aux.getPrice();
        }
        return amount;
    }

    // ToDo hashmap/enum
    public Integer calculateInterest(Integer dues){
        if(dues == 1) { return 0; }
        if(dues == 3) { return 5; }
        if(dues == 6) { return 10; }
        if(dues == 9) { return 15; }
        if(dues == 12){ return 15; }
        return null;
    }
}
