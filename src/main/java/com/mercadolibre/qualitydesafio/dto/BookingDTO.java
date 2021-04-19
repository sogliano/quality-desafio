package com.mercadolibre.qualitydesafio.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private String dateFrom;
    private String dateTo;
    private String destination;
    private String hotelCode;
    private String peopleAmount;
    private String roomType;
    private List<PeopleDTO> people;
    private PaymentMethodDTO paymentMethod;
}
