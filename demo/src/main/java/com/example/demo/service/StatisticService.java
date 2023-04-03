package com.example.demo.service;

import com.example.demo.dto.StatisticDTO;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {

    private final CustomerRepository customerRepository;

    @Autowired
    public StatisticService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }




    public List<StatisticDTO> getCustomerStatistics(Long customerId) throws Exception {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new Exception("Customer with id " + customerId + " not found"));
        List<OrderEntity> orders = customerEntity.getOrders();
        Map<String, StatisticDTO> map = new HashMap<>();

        // Iterate over all the orders for the customer
        for (OrderEntity order : orders) {
            LocalDateTime orderDate = order.getOrderDate();
            String month = orderDate.getMonth().toString();

            // If the month is not already in the map, create a new StatisticDTO object
            if (!map.containsKey(month)) {
                StatisticDTO dto = new StatisticDTO();
                dto.setMonth(month);
                dto.setTotalOrderCount(0);
                dto.setTotalBookCount(0);
                dto.setTotalPurchasedAmount(0L);
                map.put(month, dto);
            }

            // Update the statistics for the current month
            StatisticDTO dto = map.get(month);
            dto.setTotalOrderCount(dto.getTotalOrderCount() + 1);

            List<OrderDetailEntity> orderDetails = order.getOrderDetailEntities();
            for (OrderDetailEntity detail : orderDetails) {
                dto.setTotalBookCount(dto.getTotalBookCount() + detail.getCount());
                dto.setTotalPurchasedAmount(dto.getTotalPurchasedAmount() + detail.getBook().getPrice().longValue() * detail.getCount());
            }
        }

        // Return the list of StatisticDTO objects
        return new ArrayList<>(map.values());

    }
}
