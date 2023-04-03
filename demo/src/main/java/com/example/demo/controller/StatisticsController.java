package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.StatisticDTO;
import com.example.demo.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/statistic")
public class StatisticsController {
    private final StatisticService statisticService;
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/{id}")
    public ResponseDTO getStatistics(@PathVariable Long id) throws Exception {
        try {
            List<StatisticDTO> statisticsResponse = statisticService.getCustomerStatistics(id);
            return new ResponseDTO("Statistics are created.", "200", statisticsResponse);

        } catch (Exception e) {
            return new ResponseDTO(e.getMessage(),"500",null);
        }


    }
}
