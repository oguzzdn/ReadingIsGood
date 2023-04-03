package com.example.demo.repository;

import com.example.demo.DemoApplication;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.entity.BookEntity;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void createOrderTest() throws Exception {
        CustomerEntity entity =new CustomerEntity();
        entity.setName("Oguz Ozden");
        entity.setEmail("test@test.com");
        customerRepository.save(entity);

        BookEntity bookEntity =new BookEntity();
        bookEntity.setTitle("The Great Gatsby");
        bookEntity.setAuthor("F. Scott Fitzgerald");
        bookEntity.setDescription("A novel about the decadence and excess of the Roaring Twenties.");
        bookEntity.setPrice(15.99);
        bookEntity.setStock(10);
        bookRepository.save(bookEntity);

        List<OrderDetailEntity> list=new ArrayList<>();
        OrderDetailEntity detail=new OrderDetailEntity();
        detail.setBook(bookEntity);
        detail.setCount(5);
        OrderEntity order=new OrderEntity();
        detail.setOrder(order);
        list.add(detail);
        order.setCustomer(entity);
        order.setOrderDate(LocalDateTime.of(2023, Month.JANUARY,2,0,0));
        order.setOrderDetailEntities(list);

        orderRepository.save(order);
        orderDetailRepository.save(detail);

        OrderEntity result=orderRepository.getById(order.getId());
        assertEquals(order.getId(),result.getId());

    }
}
