package com.webapp.rentalapp.model;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "equipments_orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_id")
    private Long id;
    @Column(name = "client_name")
    private String clientName;
    @ManyToOne
    private Equipment equipment;



    public Order( Equipment equipment, String clientName)
    {
        this.clientName=clientName;
        this.equipment=equipment;
    }

    public Order() {

    }

    public String getClientName() {
        return clientName;
    }

    public void setClient(String clientName) {
        this.clientName = clientName;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }





    @Override
    public String toString() {
        return "Order{" +
                "Id=" + id +
                ", clientName='" + clientName + '\'' +
                ", equipment=" + equipment +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }
}
