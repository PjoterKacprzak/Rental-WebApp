package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
    Equipment findByEqName(String username);


    @Query("SELECT t FROM Equipment t WHERE t.id = ?1")
    Equipment findByID(Long id);

    @Transactional
    @Modifying
    @Query("delete from Equipment where id=?1")
    void deleteEquipmentBy(Long variable);

    @Transactional
    @Modifying
    @Query("update  Equipment set status ='pending' where id=?1")
    void setStatusToPending(Long variable);

    @Transactional
    @Modifying
    @Query("update  Equipment set status ='in_cart' where id=?1")
    void setStatusToInCart(Long variable);


    @Query("Select t From Equipment t WHERE t.status='available'")
    List<Equipment> findAllByStatusAvailable();
    @Query("Select t From Equipment t WHERE t.status='pending'")
    List<Equipment> findAllByStatusPending();
    @Query("Select t From Equipment t WHERE t.status='in_cart'")
    List<Equipment> findAllByStatusInCart();

}

