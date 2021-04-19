package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(){
        super("'roomType' parameter is not valid. It should be 'single', 'doble', 'triple', or 'multiple'.");
    }
}
