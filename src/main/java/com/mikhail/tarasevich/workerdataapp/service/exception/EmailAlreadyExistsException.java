package com.mikhail.tarasevich.workerdataapp.service.exception;

public class EmailAlreadyExistsException extends IllegalArgumentException{

    public EmailAlreadyExistsException(String errMessage){
        super(errMessage);
    }

}
