package com.example.demo.repository;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    List<OrderEntity> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<OrderEntity> findByCustomerId(Long customerId);
}
