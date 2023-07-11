package com.mikhail.tarasevich.workerdataapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private LocalDate birthDate;

    private String email;

    private String phoneNumber;

}
