package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;


    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseDTO addCustomer(CustomerDTO customerDto) {
        if (!customerDto.isValid()){
            return new ResponseDTO("Invalid Customer detail","500",customerDto);
        }else if(!isValidCustomer(customerDto.getEmail())){
            return new ResponseDTO("Mail already in use!","409",customerDto);
        }
        CustomerEntity customerEntity = convertToEntity(customerDto);
        customerEntity = customerRepository.save(customerEntity);
        return new ResponseDTO(customerEntity.getName()+" succesfully created ","0",convertToDTO(customerEntity));
    }

    private boolean isValidCustomer(String email) {
       return !customerRepository.existsByEmail(email);
    }

    public CustomerDTO convertToDTO(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerEntity.getId());
        customerDTO.setName(customerEntity.getName());
        customerDTO.setEmail(customerEntity.getEmail());
        return customerDTO;
    }

    public CustomerEntity convertToEntity(CustomerDTO customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerDto.getId());
        customerEntity.setName(customerDto.getName());
        customerEntity.setEmail(customerDto.getEmail());
        return customerEntity;
    }

}
