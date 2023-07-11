package com.mikhail.tarasevich.workerdataapp.service;

import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserResponse;

public interface UserService extends CrudService<UserRequest, UserResponse> {

    UserResponse findByEmail(String email);

}
