package com.webapp.rentalapp.model;


import javax.persistence.*;

@Entity
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "eq_name")
    private String eqName;
    @Column(name = "eq_type")
    private String eqType;
    @Column(name = "eq_mark")
    private String eqMark;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private String quantity;

    public String getEqName() {
        return eqName;
    }

    public void setEqName(String eqName) {
        this.eqName = eqName;
    }

    public String getEqType() {
        return eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqMark() {
        return eqMark;
    }

    public void setEqMark(String eqMark) {
        this.eqMark = eqMark;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
