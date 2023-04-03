package com.example.demo.dto;

import com.example.demo.entity.OrderDetailEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long customerId;
    private List<OrderDetailDTO> orderDetailEntities;
    private LocalDateTime orderDate;

    private BigDecimal totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetailDTO> getOrderDetailEntities() {
        return orderDetailEntities;
    }

    public void setOrderDetailEntities(List<OrderDetailDTO> orderDetailEntities) {
        this.orderDetailEntities = orderDetailEntities;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    private String getOrderDetailsAsString(){
        StringBuilder detail=new StringBuilder("");
        for(OrderDetailDTO dto:orderDetailEntities){
            detail.append("{ Book id= "+dto.getBookId()+", count= "+dto.getCount()+"}");
        }
        return detail.toString();
    }

    @Override
    public String toString() {
        return "Order ID=" + id +
                ", customerId=" + customerId +
                ", Details=" + getOrderDetailsAsString() +
                ", orderDate= " + orderDate +
                ", totalPrice=" + totalPrice ;
    }
}

