package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table
public class OrderDetailEntity {
    @Id
    @SequenceGenerator(
            name="order_detail_sequence",
            sequenceName="order_detail_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_detail_sequence"
    )
    private Long id;
    @ManyToOne
    private OrderEntity order;
    @ManyToOne
    private BookEntity book;

    private Integer count;

    public OrderDetailEntity() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public OrderDetailEntity(OrderEntity order, BookEntity book) {
        this.order = order;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }
}
