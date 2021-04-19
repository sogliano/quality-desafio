package com.mercadolibre.qualitydesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private String code;
    private String name;
    private String city;
    private String roomType;
    private Integer price;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String reserved;
}
