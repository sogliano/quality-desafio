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
}
