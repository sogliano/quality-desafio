package com.mercadolibre.qualitydesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleDTO {
    private Long dni;
    private String name;
    private String lastName;
    private String birthDate;
    private String mail;
}
