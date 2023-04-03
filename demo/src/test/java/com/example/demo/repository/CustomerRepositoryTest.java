package com.example.demo.repository;

import com.example.demo.DemoApplication;
import com.example.demo.entity.CustomerEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    public void createCustomerTest() {

        CustomerEntity entity =new CustomerEntity();
        entity.setName("Oguz Ozden");
        entity.setEmail("test@test.com");

        customerRepository.save(entity);

        CustomerEntity result = customerRepository.getById(entity.getId());

        assertEquals(result.getId(),entity.getId());
    }
    @Test
    @Transactional
    public void customerExistTest() {

        CustomerEntity entity =new CustomerEntity();
        entity.setName("Oguz Ozden");
        entity.setEmail("test@test.com");

        customerRepository.save(entity);

        assertTrue(customerRepository.existsById(entity.getId()));
    }
}
