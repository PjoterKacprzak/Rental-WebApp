package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import com.webapp.rentalapp.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
    Equipment findByEqName(String username);

    }

