package com.mikhail.tarasevich.workerdataapp.repository.impl;

import com.mikhail.tarasevich.workerdataapp.config.SpringTestConfig;
import com.mikhail.tarasevich.workerdataapp.model.entity.User;
import com.mikhail.tarasevich.workerdataapp.repository.UserRepository;
import com.mikhail.tarasevich.workerdataapp.service.exception.IncorrectDataBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = SpringTestConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final User user1 = User.builder()
            .withId(1L)
            .withLastName("admin")
            .withFirstName("admin")
            .withPatronymic("admin")
            .withEmail("admin@example.com")
            .withPassword("1111")
            .withBirthDate(null)
            .withPhoneNumber("89115432323")
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

    private final User user3 = User.builder()
            .withId(3L)
            .withLastName("Harris")
            .withFirstName("Matthew")
            .withPatronymic("William")
            .withEmail("matthew.harris@example.com")
            .withPassword("2143")
            .withBirthDate(LocalDate.of(1984, 4, 6))
            .withPhoneNumber("9999999999")
            .build();

    private final List<User> usersInDb = List.of(user1, user2, user3);

    @Test
    @Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void save_inputCorrectEntity_expectedEntityWithId() {

        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users",
                "email = 'ivanov@gmail.com'"));

        User user = User.builder()
                .withLastName("Ivanov")
                .withFirstName("Ivan")
                .withPatronymic("Ivanovich")
                .withEmail("ivanov@gmail.com")
                .withPassword("1111")
                .withBirthDate(LocalDate.of(2000, 5, 5))
                .withPhoneNumber("2345567")
                .build();

        assertNull(user.getId());

        userRepository.save(user);

        assertNotNull(user.getId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users",
                "email = 'ivanov@gmail.com'"));
    }

    @Test
    void findById_inputCorrectId_expectedEntityFromDB() {

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new IncorrectDataBaseException("Test DB do not have this user"));

        assertEquals(user1, user);
    }

    @Test
    void findAll_inputNothing_expectedListEntityFromDB() {

        List<User> users = userRepository.findAll();

        assertEquals(usersInDb, users);
    }

    @Test
    void findByName_inputExistingEmail_expectedEntityFromDb() {

        User user = userRepository.findByName("john.smith@example.com")
                .orElseThrow(() -> new IncorrectDataBaseException("Test DB do not have this user"));

        assertEquals(user2, user);
    }

    @Test
    void findByName_inputNonExistingEmail_expectedEmptyOptional() {

        Optional<User> user = userRepository.findByName("none@example.com");

        assertFalse(user.isPresent());
    }

    @Test
    @Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_inputUpdatedEntity_expectedUpdatedEntityInDB() {

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users",
                "email = 'john.smith@example.com'"));

        User user = user2;
        user.setEmail("updated@example.com");

        userRepository.update(user);

        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users",
                "email = 'john.smith@example.com'"));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users",
                "email = 'updated@example.com'"));
    }

    @Test
    @Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteById_inputIdFromDb_expectedEntityWithIdeDeletedFormDb() {

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users",
                "id = 3"));

        userRepository.deleteById(3L);

        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users",
                "id = 3"));
    }

}