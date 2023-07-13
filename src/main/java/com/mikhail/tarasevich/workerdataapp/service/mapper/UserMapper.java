package com.mikhail.tarasevich.workerdataapp.service.mapper;

import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserResponse;
import com.mikhail.tarasevich.workerdataapp.model.entity.User;

public interface UserMapper {

    User toEntity(UserRequest request);

    UserResponse toResponse(User user);

    UserRequest responseToRequest(UserResponse response);

}