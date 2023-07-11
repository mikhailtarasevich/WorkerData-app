package com.mikhail.tarasevich.workerdataapp.service.validator;

import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;

public interface UserValidator {

    void validateUserNameNotNullOrEmpty(UserRequest request);

    void validateEmail(UserRequest request);

    void validatePassword(UserRequest request);

}
