package com.mikhail.tarasevich.workerdataapp.service.exception;

public class IncorrectDataBaseException extends IllegalArgumentException{

    public IncorrectDataBaseException(String errMessage){
        super(errMessage);
    }

}
