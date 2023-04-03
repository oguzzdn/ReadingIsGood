package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    @Transactional
    public void addCustomerServiceTest() throws Exception {
        CustomerDTO newCustomerdto = new CustomerDTO();
        newCustomerdto.setName("getir");
        newCustomerdto.setEmail("getir@getir.com");
        ResponseDTO responseDTO=customerService.addCustomer(newCustomerdto);
        CustomerDTO dto= (CustomerDTO) responseDTO.getData();
        CustomerEntity result = customerRepository.findById(dto.getId())
                .orElseThrow(() -> new Exception("Customer not found "));
        Assert.assertTrue(result.getId() == dto.getId());
    }
    @Test
    @Transactional
    public void invalidCustomer() throws Exception {
        CustomerDTO newCustomerdto = new CustomerDTO();
        newCustomerdto.setName("");
        newCustomerdto.setEmail("getir@getir.com");
        ResponseDTO responseDTO=customerService.addCustomer(newCustomerdto);
        assertEquals("Invalid Customer detail", responseDTO.getMessage());
        assertEquals("500", responseDTO.getStatus());
    }
    @Test
    @Transactional
    public void invalidCustomer2() throws Exception {
        CustomerDTO newCustomerdto = new CustomerDTO();
        newCustomerdto.setName("");
        newCustomerdto.setEmail("getir@getir.com");
        ResponseDTO responseDTO=customerService.addCustomer(newCustomerdto);
        assertEquals("Invalid Customer detail", responseDTO.getMessage());
        assertEquals("500", responseDTO.getStatus());
    }


    @Test
    @Transactional
    public void isEmailExistServiceTest() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName("getir");
        dto.setEmail("getir@getir.com");
        customerService.addCustomer(dto);
        ResponseDTO responseDTO=customerService.addCustomer(dto);
        assertEquals("Mail already in use!", responseDTO.getMessage());
        assertEquals("409", responseDTO.getStatus());
    }

}
