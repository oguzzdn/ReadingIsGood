package com.example.demo.controller;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseDTO addCustomer(@RequestBody CustomerDTO customerDto) {
        ResponseDTO responseDTO = customerService.addCustomer(customerDto);
        return responseDTO;
    }

    @GetMapping("/{id}/orders")
    public ResponseDTO getCustomerOrders(
            @PathVariable Long id
    ) {
        try {
            List<OrderDTO> orderDtos = orderService.getCustomerOders(id);
            return new ResponseDTO("Orders founded.","200",orderDtos);
        } catch (Exception e) {
            logger.error("<CustomerController> - <getCustomerOrders> - Throw Error." + e);
            return new ResponseDTO(e.getMessage(),"500",null);
        }

    }

}

