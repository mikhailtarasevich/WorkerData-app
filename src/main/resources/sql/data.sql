INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO users (last_name, first_name, patronymic, birth_date, email, password, phone_number, role_id)
VALUES ('admin', 'admin', 'admin', NULL, 'admin', '$2a$10$7AWA81RSI5dztFN/20xfmejLtJnrPHrCmTr9wvVQ3SG0HElahHo0O', '89115432323', 1),
       ('Smith', 'John', 'Robert', '1990-05-15', 'john.smith@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '1234567890', 2),
       ('Johnson', 'Michael', 'David', '1985-09-22', 'michael.johnson@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '9876543210', 2),
       ('Williams', 'Jennifer', 'Elizabeth', '1992-03-10', 'jennifer.williams@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '5555555555', 2),
       ('Brown', 'David', 'Michael', '1988-11-28', 'david.brown@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '1111111111', 2),
       ('Taylor', 'Sarah', 'Ann', '1995-07-02', 'sarah.taylor@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '2222222222', 2),
       ('Miller', 'Christopher', 'James', '1998-01-18', 'christopher.miller@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '3333333333', 2),
       ('Anderson', 'Jessica', 'Marie', '1993-06-07', 'jessica.anderson@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '4444444444', 2),
       ('Thomas', 'Daniel', 'Edward', '1986-12-25', 'daniel.thomas@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '6666666666', 2),
       ('Jackson', 'Emily', 'Grace', '1991-08-14', 'emily.jackson@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '7777777777', 2),
       ('Harris', 'Matthew', 'William', '1984-04-06', 'matthew.harris@example.com', '$2a$10$9LBpAplZfA8SLaD1iLKtcedvzpPWdRD/aIKzCqlWZcZhL8hZ8KgQK', '9999999999', 2);
