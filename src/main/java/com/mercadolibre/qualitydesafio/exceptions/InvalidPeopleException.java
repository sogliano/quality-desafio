package com.mercadolibre.qualitydesafio.exceptions;

public class InvalidPeopleException extends Exception {
    public InvalidPeopleException(){
        super("People entered is not valid. 'peopleAmount' and People list's size aren't the same.");
    }
}
