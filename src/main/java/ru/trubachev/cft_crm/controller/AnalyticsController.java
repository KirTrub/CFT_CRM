package ru.trubachev.cft_crm.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.trubachev.cft_crm.dto.analytics.LessAmountResponse;
import ru.trubachev.cft_crm.dto.analytics.TopSellerResponse;
import ru.trubachev.cft_crm.service.AnalyticsService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService service;

    @GetMapping("/topSeller")
    public TopSellerResponse getTopSeller(@RequestParam String period, @RequestParam String date, @RequestParam(required = false, defaultValue = "1") int quarter) {
        return service.getTopSeller(period, date, quarter);
    }

    @GetMapping("/lessAmount")
    public List<LessAmountResponse> getLessAmount(@RequestParam BigDecimal amount, @RequestParam String startDate, @RequestParam String endDate){
        return service.getLessAmount(amount, startDate, endDate);
    }
}
