package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.OrderEntity;
import com.example.demo.service.BookService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService, BookService bookService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseDTO createOrder(@RequestBody OrderDTO orderDTO) throws Exception {
        try {
            ResponseDTO responseDTO = orderService.createOrder(orderDTO);
            return responseDTO;
        } catch (Exception e) {
            logger.error("<OrderController> - <addOrder> - Throw Error." + e);
            return new ResponseDTO(e.getMessage(),"500",null);
        }

    }

    @GetMapping("/{id}")
    public ResponseDTO getOrderById(@PathVariable Long id) throws Exception {
        try {
            OrderDTO orderDTO = orderService.getOrderById(id);
            if (orderDTO==null)
                return new ResponseDTO("No orders founded.","200",orderDTO);
            else
                return new ResponseDTO("Order founded.","200",orderDTO);
        } catch (Exception e) {
            logger.error("<OrderController> - <getOrderById> - Throw Error." + e);
            return new ResponseDTO(e.getMessage(),"500",null);
        }
    }

    @PostMapping("/getOrdersByDateInterval")
    public ResponseDTO getOrdersByDateInterval(@RequestBody DateDTO dateDTO
    ) {
        try {
            List<OrderDTO> orderDtos = orderService.getOrdersByDateInterval(dateDTO.getStartDate(),dateDTO.getEndDate());
            if (orderDtos==null||orderDtos.isEmpty())
                return new ResponseDTO("No orders founded.","200",orderDtos);
            else
                return new ResponseDTO("Orders founded.","200",orderDtos);
        } catch (Exception e) {
            logger.error("<CustomerController> - <getCustomerOrders> - Throw Error." + e);
            return new ResponseDTO(e.getMessage(),"500",null);
        }
    }
}
