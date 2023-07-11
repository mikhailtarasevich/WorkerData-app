package com.mikhail.tarasevich.workerdataapp.service.exception;

public class IncorrectRequestDataException extends IllegalArgumentException{

    public IncorrectRequestDataException(String errMessage){
        super(errMessage);
    }

}
