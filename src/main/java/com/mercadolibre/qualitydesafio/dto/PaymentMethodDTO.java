package com.mercadolibre.qualitydesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDTO {
    private String type;
    private String number;
    private Integer dues;
}
