package ru.trubachev.cft_crm.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.trubachev.cft_crm.dto.analytics.LessAmountResponse;
import ru.trubachev.cft_crm.dto.analytics.TopSellerResponse;
import ru.trubachev.cft_crm.service.AnalyticsService;

@WebMvcTest(AnalyticsController.class)
public class AnalyticsControllerTest {

    @MockitoBean
    private AnalyticsService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTopSeller() throws Exception {
        String period = "month";
        String date = "2026-01-01";
        int quarter = 1;

        TopSellerResponse response = new TopSellerResponse(
            1L,
            "Test Seller",
            "test@mail.ru",
            BigDecimal.valueOf(1000.00),
            period
        );

        when(service.getTopSeller(period, date, quarter)).thenReturn(response);

        final MediaType application_JSON2 = MediaType.APPLICATION_JSON;
        if (application_JSON2 != null) {
            mockMvc
                .perform(
                    MockMvcRequestBuilders.get("/analytics/topSeller")
                        .contentType(application_JSON2)
                        .param("period", period)
                        .param("date", date)
                )
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.sellerId").value(
                        response.sellerId
                    )
                )
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.name").value(
                        response.name
                    )
                )
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.contactInfo").value(
                        response.contactInfo
                    )
                )
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.totalAmount").value(
                        response.totalAmount
                    )
                )
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.period").value(period)
                );
        } else {
            mockMvc
                .perform(
                    MockMvcRequestBuilders.get("/analytics/topSeller")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("period", period)
                        .param("date", date)
                )
                .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void testGetLessAmount() throws Exception {
        BigDecimal amount = BigDecimal.valueOf(300.00);
        String startDate = "2026-01-01";
        String endDate = "2026-01-31";

        List<LessAmountResponse> response = new ArrayList<>();

        LessAmountResponse response1 = new LessAmountResponse(
            2L,
            "Test Seller2",
            "test2@mail.ru",
            BigDecimal.valueOf(200.00)
        );
        response.add(response1);

        when(service.getLessAmount(amount, startDate, endDate)).thenReturn(
            response
        );

        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/analytics/lessAmount")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("amount", amount.toString())
                    .param("startDate", startDate)
                    .param("endDate", endDate)
            )
            .andExpect(status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(
                    response.get(0).getId()
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(
                    response.get(0).getName()
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$[0].contactInfo").value(
                    response.get(0).getContactInfo()
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$[0].totalAmount").value(
                    response.get(0).getTotalAmount()
                )
            );
    }
}
