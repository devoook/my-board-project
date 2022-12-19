package com.myproject.boardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MyBoardProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBoardProjectApplication.class, args);
    }

}
