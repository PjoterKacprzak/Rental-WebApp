package com.webapp.rentalapp.model;


import javax.persistence.*;



@Entity
@Table(name = "equipments")
public class Equipment {

    public enum EquipmentStatus
    {
        available,
        pending,
        not_available,
        in_cart,
        accepted
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EquipmentStatus status;


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

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "Id=" + id +
                ", eqName='" + eqName + '\'' +
                ", eqType='" + eqType + '\'' +
                ", eqMark='" + eqMark + '\'' +
                ", price=" + price +
                ", quantity='" + quantity + '\'' +
                ", status=" + status +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }
}
