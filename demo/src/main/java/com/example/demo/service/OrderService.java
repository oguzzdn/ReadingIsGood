package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.BookEntity;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, BookRepository bookRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.orderDetailRepository = orderDetailRepository;
    }
    @Transactional
    public ResponseDTO createOrder(OrderDTO orderDTO) throws Exception {
        CustomerEntity customerEntity = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new Exception("Customer with id " + orderDTO.getCustomerId() + " not found"));

        List<OrderDetailEntity> orderDetails=getOrderDetailsFromDTO(orderDTO.getOrderDetailEntities());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomer(customerEntity);
        orderEntity.setOrderDate(orderDTO.getOrderDate());

        validateOrder(orderEntity,orderDetails);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderDetailEntity detail : orderDetails) {
            totalPrice = totalPrice.add((detail.getBook().getPrice()).multiply(BigDecimal.valueOf(detail.getCount())));
        }
        orderEntity.setTotalPrice(totalPrice);
        orderEntity = orderRepository.save(orderEntity);
        //Update book stock and set details
        for (OrderDetailEntity detail : orderDetails) {
            BookEntity book=detail.getBook();
            book.setStock(book.getStock() - detail.getCount());
            bookRepository.save(book);
            detail.setOrder(orderEntity);
        }
        orderDetailRepository.saveAll(orderDetails);
        orderEntity.setOrderDetailEntities(orderDetails);

        return new ResponseDTO("Order Successfully Created", "200",getOrderDTO(orderEntity));

    }

    private void validateOrder(OrderEntity orderEntity, List<OrderDetailEntity> orderDetails) throws Exception {
        if (orderDetails.isEmpty() ) {
            throw new Exception("Order Details is Empty!");
        }else if (orderEntity.getOrderDate() == null){
            throw new Exception("Order Date is Empty!");
        }
        for(OrderDetailEntity orderDetail:orderDetails){
            if(orderDetail.getBook().getStock()<orderDetail.getCount())
                throw new Exception("Not enough book for BookId="+ orderDetail.getBook().getId());
            else if(orderDetail.getCount()<0)
                throw new Exception("Count cant be negative");
        }


    }

    private List<OrderDetailEntity> getOrderDetailsFromDTO(List<OrderDetailDTO> orderDetailEntities) throws Exception {
        List<OrderDetailEntity> entities= new ArrayList<>();
        for(OrderDetailDTO dto:orderDetailEntities){
            OrderDetailEntity temp = new OrderDetailEntity();
            BookEntity book = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new Exception("Book with id " + dto.getBookId() + " not found"));
            temp.setBook(book);
            temp.setCount(dto.getCount());
            entities.add(temp);
        }
            return entities;
    }


    public OrderDTO getOrderById(Long orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order with id " + orderId + " not found"));
        return getOrderDTO(orderEntity);
    }

    public List<OrderDTO> getCustomerOders(Long customerId) throws Exception {
        if(!customerRepository.existsById(customerId))
            throw new Exception("Customer with id " + customerId + " not found");
        List<OrderEntity> orders = orderRepository.findByCustomerId(customerId);
        List<OrderDTO> dtos = new ArrayList<>();
        for (OrderEntity orderEntity : orders){
            dtos.add(getOrderDTO(orderEntity));
        }
        return dtos;
    }
    public List<OrderDTO> getOrdersByDateInterval(LocalDateTime startDate,LocalDateTime endDate) throws Exception {
        List<OrderEntity> orderEntities=orderRepository.findByOrderDateBetween(startDate,endDate);
        List<OrderDTO> dtos = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities){
            dtos.add(getOrderDTO(orderEntity));
        }

        return dtos;
    }

    private OrderDTO getOrderDTO(OrderEntity orderEntity) {
        List<OrderDetailEntity> entities = orderEntity.getOrderDetailEntities();
        List<OrderDetailDTO> orderDetailDtos = new ArrayList<>();
        for (OrderDetailEntity detailEntity : entities) {
            OrderDetailDTO dto=new OrderDetailDTO();
            dto.setCount(detailEntity.getCount());
            dto.setBookId(detailEntity.getBook().getId());
            orderDetailDtos.add(dto);
        }
        OrderDTO createdOrderDTO = new OrderDTO();
        createdOrderDTO.setOrderDetailEntities(orderDetailDtos);
        createdOrderDTO.setId(orderEntity.getId());
        createdOrderDTO.setCustomerId(orderEntity.getCustomer().getId());
        createdOrderDTO.setTotalPrice(orderEntity.getTotalPrice());
        createdOrderDTO.setOrderDate(orderEntity.getOrderDate());
        return createdOrderDTO;
    }

}
