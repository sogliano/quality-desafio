package com.mercadolibre.qualitydesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private Integer price;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
