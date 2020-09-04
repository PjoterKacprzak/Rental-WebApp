package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);


}
