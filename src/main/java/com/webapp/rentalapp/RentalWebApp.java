package com.webapp.rentalapp;

import com.webapp.rentalapp.controller.ClientRepository;
import com.webapp.rentalapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

@SpringBootApplication
@Sql("/data.sql")
public class RentalWebApp implements CommandLineRunner {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(RentalWebApp.class, args);

    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
