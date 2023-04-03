package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    public OrderDTO createMockOrder(LocalDateTime date, Long customerId
            ,Long firstBookId,int firstBookCount,Long secondBookId,int secondBookCount){
        if(customerId==null){
            CustomerDTO dto = new CustomerDTO();
            dto.setName("getir");
            dto.setEmail("getir@getir.com");
            ResponseDTO responseDTO=customerService.addCustomer(dto);
            customerId= ((CustomerDTO) responseDTO.getData()).getId();
        }

        OrderDetailDTO first=new OrderDetailDTO();
        first.setBookId(firstBookId);
        first.setCount(firstBookCount);
        OrderDetailDTO second=new OrderDetailDTO();
        second.setBookId(secondBookId);
        second.setCount(secondBookCount);
        List<OrderDetailDTO> orderDetais=List.of(first,second);

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setOrderDate(date==null? LocalDateTime.now():date);
        orderDTO.setCustomerId(customerId);
        orderDTO.setOrderDetailEntities(orderDetais);
        return orderDTO;
    }
    @Test
    @Transactional
    public void createOrderTest() throws Exception {
        OrderDTO orderDTO=createMockOrder(null,null,1l,1,2l,2);
        ResponseDTO responseDTO = orderService.createOrder(orderDTO);
        assertNotNull(responseDTO);
        assertEquals("Order Successfully Created", responseDTO.getMessage());
        assertEquals("200", responseDTO.getStatus());
    }
    @Test
    @Transactional
    public void getOrderIntervalTest() throws Exception {
        OrderDTO orderDTO=createMockOrder(LocalDateTime.of(2023, Month.JANUARY,2,0,0),null,1l,1,2l,1);
        ResponseDTO responseDTO = orderService.createOrder(orderDTO);
        assertNotNull(responseDTO);
        assertEquals("Order Successfully Created", responseDTO.getMessage());
        assertEquals("200", responseDTO.getStatus());
        List<OrderDTO> orders = orderService.getOrdersByDateInterval(LocalDateTime.of(2022, Month.JANUARY, 2, 0, 0), LocalDateTime.of(2024, Month.JANUARY, 2, 0, 0));
        assertEquals(1l,orders.size());
    }
    @Test
    @Transactional
    public void getOrderIntervalEmptyTest() throws Exception {
        List<OrderDTO> orders = orderService.getOrdersByDateInterval(LocalDateTime.of(2022, Month.JANUARY, 2, 0, 0), LocalDateTime.of(2024, Month.JANUARY, 2, 0, 0));
        assertTrue(orders.isEmpty());
    }


    @Test
    @Transactional
    public void getOrdersTest() throws Exception {
        OrderDTO orderDTO=createMockOrder(null,null,1l,1,2l,2);
        ResponseDTO responseDTO = orderService.createOrder(orderDTO);
        assertNotNull(responseDTO);
        assertEquals("Order Successfully Created", responseDTO.getMessage());
        assertEquals("200", responseDTO.getStatus());
        Long customerId=((OrderDTO)responseDTO.getData()).getCustomerId();
        List<OrderEntity> orders = orderRepository.findByCustomerId(customerId);
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }
    @Test
    @Transactional
    public void getNotExistingCustomersOrdersTest() throws Exception {
        try {
            List<OrderDTO> orders = orderService.getCustomerOders(-1l);
        }catch (Exception e){
            assertEquals(e.getMessage(),"Customer with id -1 not found");
        }


    }
    @Test
    @Transactional
    public void findOrderDetailTest() throws Exception {
        OrderDTO orderDTO=createMockOrder(null,null,1l,1,2l,2);
        ResponseDTO responseDTO = orderService.createOrder(orderDTO);
        Long orderId=((OrderDTO)responseDTO.getData()).getId();
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order with id " + orderId + " not found"));
        assertNotNull(responseDTO);
        assertEquals("Order Successfully Created", responseDTO.getMessage());
        assertEquals("200", responseDTO.getStatus());
        assertEquals(orderId,orderEntity.getId());
    }

    @Test
    @Transactional
    public void findNonExistingOrderTest() throws Exception {
        try{
        OrderDTO foundedOrderDTO=orderService.getOrderById(-1l);
        }
        catch(Exception e){
            assertEquals(e.getMessage(),"Order with id -1 not found");
        }
    }


}
