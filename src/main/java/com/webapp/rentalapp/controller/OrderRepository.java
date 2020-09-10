package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import com.webapp.rentalapp.model.Equipment;
import com.webapp.rentalapp.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    //OrderRepository findByUsername(String string);


    Order findById(Long id);

    @Query("Select t From Order t WHERE t.clientName=?1 AND t.equipment.status='in_cart'")
    List<Order> findAllByStatusInCart(String string);

    @Query("Select t From Order t WHERE t.clientName=?1 AND t.equipment.status='pending'")
    List<Order> findAllByStatusPending(String string);

    @Query("Select t From Order t WHERE t.equipment.status='pending'")
    List<Order> findOnlyByStatusPending();

    @Query("Select t from Order t where t.clientName =?1 and t.equipment.status='in_cart' or t.equipment.status = 'accepted'")
    List<Order>findByNameAndStatus(String string);
//    @Transactional
//    @Modifying
//    @Query("update  Order t set t.equipment.status=?1 where t.Id =?2")
//    void setCartToPending(Enum variable1, Long variable2);

    @Transactional
    @Modifying
    @Query("update  Order t set t.equipment.status=?1 where t.clientName =?2")
    void setCartToPending(Enum variable1, String variable2);



}
