package com.mikhail.tarasevich.workerdataapp.service.validator.impl;

import com.mikhail.tarasevich.workerdataapp.repository.UserRepository;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.service.exception.EmailAlreadyExistsException;
import com.mikhail.tarasevich.workerdataapp.service.exception.IncorrectRequestDataException;
import com.mikhail.tarasevich.workerdataapp.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidatorImpl implements UserValidator {

    private final UserRepository userDao;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    @Autowired
    public UserValidatorImpl(UserRepository userDao) {
        this.userDao = userDao;
    }

    @Override
    public void validateUserNameNotNullOrEmpty(UserRequest request) {

        String firstName = request.getFirstName();
        String lastName = request.getLastName();

        if (firstName == null || firstName.replaceAll("\\s", "").equals("")) {
            throw new IncorrectRequestDataException("User's first name can't be null or empty.");
        }

        if (lastName == null || lastName.replaceAll("\\s", "").equals("")) {
            throw new IncorrectRequestDataException("User's last name can't be null or empty.");
        }

        if (firstName.length() < 2 || firstName.length() >= 30) {
            throw new IncorrectRequestDataException("User's first name should be between 1 and 30 characters.");
        }

        if (lastName.length() < 2 || lastName.length() >= 30) {
            throw new IncorrectRequestDataException("User's last name should be between 1 and 30 characters.");
        }
    }

    @Override
    public void validateEmail(UserRequest request) {

        String email = request.getEmail();
        Matcher matcher = emailPattern.matcher(email);

        if (!matcher.matches()) {
            throw new IncorrectRequestDataException("Email is not valid.");
        }

        if (userDao.findByName(email).isPresent()) {
            throw new EmailAlreadyExistsException("The email = " + email + " already exists in database.");
        }
    }

    @Override
    public void validatePassword(UserRequest request) {

        String password = request.getPassword();
        String confirmPassword = request.getConfirmedPassword();

        if (!password.equals(confirmPassword)) {
            throw new IncorrectRequestDataException("Entered passwords are different.");
        }

        if (password.replaceAll("\\s", "").equals("")) {
            throw new IncorrectRequestDataException("Password should not be empty.");
        }

        if (password.length() < 4) {
            throw new IncorrectRequestDataException("Password should be equal or greater than 4 symbols.");
        }
    }

}
