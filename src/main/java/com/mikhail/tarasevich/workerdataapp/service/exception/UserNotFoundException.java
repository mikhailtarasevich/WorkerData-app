package com.mikhail.tarasevich.workerdataapp.service.exception;

public class UserNotFoundException extends IllegalArgumentException{

    public UserNotFoundException(String errMessage){
        super(errMessage);
    }

}
