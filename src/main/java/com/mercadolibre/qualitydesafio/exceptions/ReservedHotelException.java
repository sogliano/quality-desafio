package com.mercadolibre.qualitydesafio.exceptions;

public class ReservedHotelException extends Exception {
    public ReservedHotelException(String code){
        super("Hotel with code '" + code + "' is already reserved.");
    }
}
