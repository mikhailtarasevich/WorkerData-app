package com.mikhail.tarasevich.workerdataapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mikhail.tarasevich.workerdataapp")
public class WorkerDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerDataApplication.class, args);
    }

}
