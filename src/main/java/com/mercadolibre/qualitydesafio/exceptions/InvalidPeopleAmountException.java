package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidPeopleAmountException extends Exception {
    public InvalidPeopleAmountException(){
        super("'peopleAmount' parameter is not valid. It should be a number.");
    }
}
