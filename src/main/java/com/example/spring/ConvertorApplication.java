package com.example.spring;

import com.example.spring.service.ConvertorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ConvertorApplication implements CommandLineRunner {

    @Autowired
    ConvertorService convertorService;
    public static void main(String[] args) {

        SpringApplication.run(ConvertorApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
        convertorService.saveAllEuroToUsd();

    }
}
