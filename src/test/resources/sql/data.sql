INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO users (last_name, first_name, patronymic, birth_date, email, password, phone_number, role_id)
VALUES ('admin', 'admin', 'admin', NULL, 'admin@example.com', '1111', '89115432323', 1),
       ('Smith', 'John', 'Robert', '1990-05-15', 'john.smith@example.com', '1234', '1234567890', 2),
       ('Harris', 'Matthew', 'William', '1984-04-06', 'matthew.harris@example.com', '2143', '9999999999', 2);
