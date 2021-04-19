package com.mercadolibre.qualitydesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO {
    private String userName;
    private BookingDTO booking;
    private ReservationDTO flightReservation;
}
