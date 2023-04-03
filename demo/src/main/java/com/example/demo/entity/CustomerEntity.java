package com.example.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class CustomerEntity {
    @Id
    @SequenceGenerator(
            name="customer_sequence",
            sequenceName="customer_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private Long id;

    private String email;

    private String name;
    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;

    public CustomerEntity() {}

    public CustomerEntity(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
