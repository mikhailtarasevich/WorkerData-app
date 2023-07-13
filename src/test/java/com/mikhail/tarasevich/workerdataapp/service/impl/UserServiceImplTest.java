package com.mikhail.tarasevich.workerdataapp.service.impl;

import com.mikhail.tarasevich.workerdataapp.model.dto.UserRequest;
import com.mikhail.tarasevich.workerdataapp.model.dto.UserResponse;
import com.mikhail.tarasevich.workerdataapp.model.entity.Role;
import com.mikhail.tarasevich.workerdataapp.model.entity.User;
import com.mikhail.tarasevich.workerdataapp.repository.RoleRepository;
import com.mikhail.tarasevich.workerdataapp.repository.UserRepository;
import com.mikhail.tarasevich.workerdataapp.service.exception.EmailAlreadyExistsException;
import com.mikhail.tarasevich.workerdataapp.service.exception.IncorrectDataBaseException;
import com.mikhail.tarasevich.workerdataapp.service.exception.IncorrectRequestDataException;
import com.mikhail.tarasevich.workerdataapp.service.mapper.UserMapper;
import com.mikhail.tarasevich.workerdataapp.service.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserValidator validator;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final User user1 = User.builder()
            .withId(1L)
            .withLastName("Harris")
            .withFirstName("Matthew")
            .withPatronymic("William")
            .withEmail("matthew.harris@example.com")
            .withPassword("2143")
            .withBirthDate(LocalDate.of(1984, 4, 6))
            .withPhoneNumber("9999999999")
            .build();

    private final User user2 = User.builder()
            .withId(2L)
            .withLastName("Smith")
            .withFirstName("John")
            .withPatronymic("Robert")
            .withEmail("john.smith@example.com")
            .withPassword("1234")
            .withBirthDate(LocalDate.of(1990, 5, 15))
            .withPhoneNumber("1234567890")
            .build();

    private final UserResponse userResponse1 = UserResponse.builder()
            .withId(1L)
            .withLastName("Harris")
            .withFirstName("Matthew")
            .withPatronymic("William")
            .withEmail("matthew.harris@example.com")
            .withBirthDate(LocalDate.of(1984, 4, 6))
            .withPhoneNumber("9999999999")
            .build();

    private final UserResponse userResponse2 = UserResponse.builder()
            .withId(2L)
            .withLastName("Smith")
            .withFirstName("John")
            .withPatronymic("Robert")
            .withEmail("john.smith@example.com")
            .withBirthDate(LocalDate.of(1990, 5, 15))
            .withPhoneNumber("1234567890")
            .build();

    private final UserRequest userRequest1 = UserRequest.builder()
            .withLastName("Harris")
            .withFirstName("Matthew")
            .withPatronymic("William")
            .withEmail("matthew.harris@example.com")
            .withPassword("2143")
            .withConfirmedPassword("2143")
            .withBirthDate(LocalDate.of(1984, 4, 6))
            .withPhoneNumber("9999999999")
            .build();

    private final Role userRole = Role.builder()
            .withId(1)
            .withName("ROLE_USER")
            .build();

    @Test
    void register_validUserRequest_successfullyRegistersUser() {

        doNothing().when(validator).validateUserNameNotNullOrEmpty(userRequest1);
        doNothing().when(validator).validateEmail(userRequest1);
        doNothing().when(validator).validatePassword(userRequest1);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(userRequest1.getPassword())).thenReturn(userRequest1.getPassword());
        when(mapper.toEntity(userRequest1)).thenReturn(user1);
        when(userRepository.findByName(userRequest1.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user1)).thenReturn(user1);
        when(mapper.toResponse(user1)).thenReturn(userResponse1);

        UserResponse savedUser = userService.register(userRequest1);

        assertNotNull(savedUser);
        assertEquals(userResponse1, savedUser);
        verify(validator, times(1)).validateUserNameNotNullOrEmpty(userRequest1);
        verify(validator, times(1)).validateEmail(userRequest1);
        verify(validator, times(1)).validatePassword(userRequest1);
        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(passwordEncoder, times(1)).encode(userRequest1.getPassword());
        verify(mapper, times(1)).toEntity(userRequest1);
        verify(userRepository, times(1)).findByName(userRequest1.getEmail());
        verify(userRepository, times(1)).save(user1);
        verify(mapper, times(1)).toResponse(user1);
        verifyNoMoreInteractions(validator, roleRepository, passwordEncoder, userRepository, mapper);
    }

    @Test
    void register_existingUserRequest_throwsEmailAlreadyExistsException() {

        when(userRepository.findByName(userRequest1.getEmail())).thenReturn(Optional.of(user1));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(userRequest1));

        verify(userRepository, times(1)).findByName(userRequest1.getEmail());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(validator, roleRepository, passwordEncoder, mapper);
    }

    @Test
    void register_missingRoleInDatabase_throwsIncorrectDataBaseException() {

        doNothing().when(validator).validateUserNameNotNullOrEmpty(userRequest1);
        doNothing().when(validator).validateEmail(userRequest1);
        doNothing().when(validator).validatePassword(userRequest1);
        when(passwordEncoder.encode(userRequest1.getPassword())).thenReturn(userRequest1.getPassword());
        when(userRepository.findByName(userRequest1.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        assertThrows(IncorrectDataBaseException.class, () -> userService.register(userRequest1));

        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(userRepository, times(1)).findByName(userRequest1.getEmail());
        verifyNoMoreInteractions(roleRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void findById_existingId_FoundEntity() {

        long id =1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(mapper.toResponse(user1)).thenReturn(userResponse1);

        UserResponse fromService = userService.findById(id);

        assertEquals(userResponse1, fromService);
        verify(userRepository, times(1)).findById(id);
        verify(mapper, times(1)).toResponse(user1);
    }

    @Test
    void findById_IdNonExist_expectedException() {

        long id =1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IncorrectRequestDataException.class, () -> userService.findById(id));

        verify(userRepository, times(1)).findById(id);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void findByEmail_existingEmail_returnsUserResponse() {

        String email = "matthew.harris@example.com";
        when(userRepository.findByName(email)).thenReturn(Optional.of(user1));
        when(mapper.toResponse(user1)).thenReturn(userResponse1);

        UserResponse result = userService.findByEmail(email);

        assertNotNull(result);
        assertEquals(userResponse1, result);
        verify(userRepository, times(1)).findByName(email);
        verify(mapper, times(1)).toResponse(user1);
    }

    @Test
    void findByEmail_nonExistingEmail_throwsIncorrectRequestDataException() {

        String email = "nonexistent@example.com";
        when(userRepository.findByName(email)).thenReturn(Optional.empty());

        assertThrows(IncorrectRequestDataException.class, () -> userService.findByEmail(email));

        verify(userRepository, times(1)).findByName(email);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void findAll_nothing_userResponseList() {

        List<UserResponse> userResponseList = List.of(userResponse1, userResponse2);
        List<User> userList = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);
        when(mapper.toResponse(user1)).thenReturn(userResponse1);
        when(mapper.toResponse(user2)).thenReturn(userResponse2);

        List<UserResponse> foundUsers = userService.findAll();

        assertEquals(userResponseList, foundUsers);
        verify(userRepository, times(1)).findAll();
        verify(mapper, times(1)).toResponse(user1);
        verify(mapper, times(1)).toResponse(user2);
        verifyNoMoreInteractions(userRepository, mapper);
    }

    @Test
    void edit_validUserRequest_updatesUser() {

        UserRequest request = UserRequest.builder()
                .withId(1L)
                .withLastName("Smith")
                .withFirstName("John")
                .withPatronymic("Robert")
                .withEmail("john.smith@example.com")
                .withPassword("newpassword")
                .withConfirmedPassword("newpassword")
                .withBirthDate(LocalDate.of(1990, 5, 15))
                .withPhoneNumber("1234567890")
                .build();

        User persistedUser = User.builder()
                .withId(1L)
                .withLastName("Doe")
                .withFirstName("Jane")
                .withPatronymic("Marie")
                .withEmail("jane.doe@example.com")
                .withPassword("password")
                .withBirthDate(LocalDate.of(1985, 3, 10))
                .withPhoneNumber("9876543210")
                .build();

        doNothing().when(validator).validateUserNameNotNullOrEmpty(request);
        when(userRepository.findById(request.getId())).thenReturn(Optional.of(persistedUser));
        when(userRepository.findByName(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedpassword");

        userService.edit(request);

        verify(validator, times(1)).validateUserNameNotNullOrEmpty(request);
        verify(userRepository, times(1)).findById(request.getId());
        verify(userRepository, times(1)).findByName(request.getEmail());
        verify(passwordEncoder, times(1)).encode("newpassword");
        verify(userRepository, times(1)).update(persistedUser);
    }

    @Test
    void edit_userNotFound_throwsIncorrectRequestDataException() {

        UserRequest request = UserRequest.builder()
                .withId(1L)
                .withLastName("Smith")
                .withFirstName("John")
                .withPatronymic("Robert")
                .withEmail("john.smith@example.com")
                .withPassword("newpassword")
                .withConfirmedPassword("newpassword")
                .withBirthDate(LocalDate.of(1990, 5, 15))
                .withPhoneNumber("1234567890")
                .build();

        doNothing().when(validator).validateUserNameNotNullOrEmpty(request);
        when(userRepository.findById(request.getId())).thenReturn(Optional.empty());

        assertThrows(IncorrectRequestDataException.class, () -> userService.edit(request));

        verify(validator, times(1)).validateUserNameNotNullOrEmpty(request);
        verify(userRepository, times(1)).findById(request.getId());
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void edit_emailAlreadyExists_throwsEmailAlreadyExistsException() {

        UserRequest request = UserRequest.builder()
                .withId(1L)
                .withLastName("Smith")
                .withFirstName("John")
                .withPatronymic("Robert")
                .withEmail("slim@example.com")
                .withPassword("newpassword")
                .withConfirmedPassword("newpassword")
                .withBirthDate(LocalDate.of(1990, 5, 15))
                .withPhoneNumber("1234567890")
                .build();

        User persistedUser = User.builder()
                .withId(1L)
                .withLastName("Doe")
                .withFirstName("Jane")
                .withPatronymic("Marie")
                .withEmail("john.smith@example.com")
                .withPassword("password")
                .withBirthDate(LocalDate.of(1985, 3, 10))
                .withPhoneNumber("9876543210")
                .build();

        User anotherUser = User.builder()
                .withId(2L)
                .withEmail("slim@example.com")
                .build();

        doNothing().when(validator).validateUserNameNotNullOrEmpty(request);
        when(userRepository.findById(request.getId())).thenReturn(Optional.of(persistedUser));
        when(userRepository.findByName(request.getEmail())).thenReturn(Optional.of(anotherUser));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.edit(request));

        verify(validator, times(1)).validateUserNameNotNullOrEmpty(request);
        verify(userRepository, times(1)).findById(request.getId());
        verify(userRepository, times(1)).findByName(request.getEmail());
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void deleteById_existingId_returnsTrue() {

        long id = 1L;
        when(userRepository.deleteById(id)).thenReturn(true);

        boolean result = userService.deleteById(id);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(id);
    }

}
