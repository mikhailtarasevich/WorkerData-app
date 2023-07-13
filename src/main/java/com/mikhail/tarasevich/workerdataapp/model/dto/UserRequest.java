package com.mikhail.tarasevich.workerdataapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {

    private Long id;

    @NotBlank(message = "Last name should be not empty")
    private String lastName;

    @NotBlank(message = "First name should be not empty")
    private String firstName;

    private String patronymic;

    @NotNull(message = "Birth date should be not empty")
    private LocalDate birthDate;

    @Email(message = "Incorrect email format")
    private String email;

    private String password;

    private String confirmedPassword;

    @NotBlank(message = "Phone number should be not empty")
    @Pattern(regexp = "^[0-9]*$", message = "Use only digits")
    private String phoneNumber;

}
