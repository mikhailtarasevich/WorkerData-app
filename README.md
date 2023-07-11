# WORKER-DATA - vaadin + spring boot app

Web application developed using Vaadin and SpringBoot. Upon launch, the user sees an authentication page and cannot
access the contact information of other users. After authentication, the system loads a table with all users for the
administrator, or a personal profile page for an authenticated user without administrator privileges. The administrator
has access to CRUD operations on users.

## Run the application

To run the application (you need to have Docker installed on your computer),
please [download the docker-compose](https://drive.google.com/drive/folders/1k2NxJv_uOalbQBzm2Nhp2upqkTdZGc0_?usp=sharing)
file. Open a terminal in the folder containing the file and
execute the command:

    docker-compose up -d

Open the link [http://localhost:8888/](http://localhost:8888/) in a web browser.

## How to use the application.

To use the application, you need to authenticate:

- As an administrator (admin:admin)
- As a user (john.smith@example.com:1111)

# Libraries and Frameworks

- Vaadin
- Spring Boot
- Spring Security
- Hibernate
- Postgres
- Junit jupiter
- Mockito
- Lombok
- Docker