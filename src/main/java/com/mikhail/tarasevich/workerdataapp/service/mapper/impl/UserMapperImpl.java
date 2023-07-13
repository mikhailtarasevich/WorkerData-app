package com.mikhail.tarasevich.workerdataapp.service.mapper.impl;

import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserResponse;
import com.mikhail.tarasevich.workerdataapp.model.entity.User;
import com.mikhail.tarasevich.workerdataapp.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest request) {

        return User.builder()
                .withId(request.getId())
                .withLastName(request.getLastName())
                .withFirstName(request.getFirstName())
                .withPatronymic(request.getPatronymic())
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .withBirthDate(request.getBirthDate())
                .withPhoneNumber(request.getPhoneNumber())
                .build();
    }

    @Override
    public UserResponse toResponse(User entity) {

        return UserResponse.builder()
                .withId(entity.getId())
                .withLastName(entity.getLastName())
                .withFirstName(entity.getFirstName())
                .withPatronymic(entity.getPatronymic())
                .withEmail(entity.getEmail())
                .withBirthDate(entity.getBirthDate())
                .withPhoneNumber(entity.getPhoneNumber())
                .build();
    }

    @Override
    public UserRequest responseToRequest(UserResponse response) {

        if (response == null) return new UserRequest();

        return UserRequest.builder()
                .withId(response.getId())
                .withLastName(response.getLastName())
                .withFirstName(response.getFirstName())
                .withPatronymic(response.getPatronymic())
                .withEmail(response.getEmail())
                .withBirthDate(response.getBirthDate())
                .withPhoneNumber(response.getPhoneNumber())
                .build();
    }

}
