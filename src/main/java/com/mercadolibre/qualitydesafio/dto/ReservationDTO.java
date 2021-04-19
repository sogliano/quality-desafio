package com.mercadolibre.qualitydesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String dateFrom;
    private String dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private String seats;
    private String seatType;
    private List<PeopleDTO> people;
    private PaymentMethodDTO paymentMethod;
}
