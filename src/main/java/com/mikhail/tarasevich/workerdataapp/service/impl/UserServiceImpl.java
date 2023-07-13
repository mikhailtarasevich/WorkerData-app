package com.mikhail.tarasevich.workerdataapp.service.impl;

import com.mikhail.tarasevich.workerdataapp.repository.RoleRepository;
import com.mikhail.tarasevich.workerdataapp.repository.UserRepository;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserResponse;
import com.mikhail.tarasevich.workerdataapp.model.entity.Role;
import com.mikhail.tarasevich.workerdataapp.model.entity.User;
import com.mikhail.tarasevich.workerdataapp.service.exception.EmailAlreadyExistsException;
import com.mikhail.tarasevich.workerdataapp.service.exception.IncorrectDataBaseException;
import com.mikhail.tarasevich.workerdataapp.service.exception.IncorrectRequestDataException;
import com.mikhail.tarasevich.workerdataapp.service.mapper.UserMapper;
import com.mikhail.tarasevich.workerdataapp.service.UserService;
import com.mikhail.tarasevich.workerdataapp.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper mapper;

    private final UserValidator validator;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userDao, RoleRepository roleDao, UserMapper mapper, UserValidator validator,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userDao;
        this.roleRepository = roleDao;
        this.mapper = mapper;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse register(UserRequest request) {

        if (checkEmail(request)) {

            validator.validateUserNameNotNullOrEmpty(request);
            validator.validateEmail(request);
            validator.validatePassword(request);

            request.setPassword(passwordEncoder.encode(request.getPassword()));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new IncorrectDataBaseException("There is no ROLE_USER in database"));

            User entity = mapper.toEntity(request);
            entity.setId(null);
            entity.setRole(userRole);

            return mapper.toResponse(userRepository.save(entity));
        } else {
            throw new EmailAlreadyExistsException("The user with email " + request.getEmail() + " already in the database");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(long id) {

        return mapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new IncorrectRequestDataException("The user with id = " + id + " doesn't exist in the database")));
    }

    @Override
    public UserResponse findByEmail(String email) {

        return mapper.toResponse(userRepository.findByName(email)
                .orElseThrow(() -> new IncorrectRequestDataException("The user with email = " + email + " doesn't exist in the database")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {

        return userRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void edit(UserRequest request) {

        validator.validateUserNameNotNullOrEmpty(request);

        User persistedUser = userRepository.findById(request.getId()).orElseThrow(() -> new IncorrectRequestDataException("There is no user in DB with id = " + request.getId()));

        persistedUser.setFirstName(request.getFirstName());
        persistedUser.setLastName(request.getLastName());
        persistedUser.setPatronymic(request.getPatronymic());
        persistedUser.setBirthDate(request.getBirthDate());
        persistedUser.setPhoneNumber(request.getPhoneNumber());

        Optional<User> foundByEmail = userRepository.findByName(request.getEmail());

        if (foundByEmail.isPresent() && !foundByEmail.get().getId().equals(request.getId())) {
            throw new EmailAlreadyExistsException("The user with email " + request.getEmail() + " already in the database");
        } else {
            persistedUser.setEmail(request.getEmail());
        }

        if (!request.getPassword().isBlank()) {
            validator.validatePassword(request);
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.update(persistedUser);
    }

    @Override
    public boolean deleteById(long id) {

        return userRepository.deleteById(id);
    }

    private boolean checkEmail(UserRequest request) {

        return userRepository.findByName(request.getEmail()).isEmpty();
    }

}
