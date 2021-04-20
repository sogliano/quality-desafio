package com.mercadolibre.qualitydesafio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private String userName;
    private Integer amount;
    private Integer interest;
    private Double total;
    private BookingDTO booking;
    private ReservationDTO flightReservation;
    private StatusDTO statusCode;

    public ResponseDTO(String userName, Integer amount, Integer interest, Double total, ReservationDTO flightReservation, StatusDTO statusCode) {
        this.userName = userName;
        this.amount = amount;
        this.interest = interest;
        this.total = total;
        this.flightReservation = flightReservation;
        this.statusCode = statusCode;
    }

    public ResponseDTO(String userName, Integer amount, Integer interest, Double total, BookingDTO booking, StatusDTO statusCode) {
        this.userName = userName;
        this.amount = amount;
        this.interest = interest;
        this.total = total;
        this.booking = booking;
        this.statusCode = statusCode;
    }
}
