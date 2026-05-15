package ru.trubachev.cft_crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.trubachev.cft_crm.dto.analytics.LessAmountResponse;
import ru.trubachev.cft_crm.dto.analytics.TopSellerResponse;
import ru.trubachev.cft_crm.repo.SellerRepo;
import ru.trubachev.cft_crm.repo.TransactionRepo;
import ru.trubachev.cft_crm.service.impl.AnalyticsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AnalyticsServiceImplTest {

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    @Mock
    private SellerRepo sellerRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Test
    void getTopSeller_day_shouldReturnTopSeller() {
        String period = "day";
        String date = "2026-01-01";
        int quarter = 1;

        TopSellerResponse expectedResponse = new TopSellerResponse(
            1L,
            "Test Seller",
            "test@example.com",
            BigDecimal.valueOf(1000.00),
            period
        );

        when(
            transactionRepo.findTopSellerByPeriod(
                any(LocalDateTime.class),
                any(LocalDateTime.class)
            )
        ).thenReturn(expectedResponse);

        TopSellerResponse response = analyticsService.getTopSeller(
            period,
            date,
            quarter
        );

        assertNotNull(response);
        assertEquals(period, response.period);
    }

    @Test
    void lessAmount_shouldReturnLessAmount() {
        BigDecimal amount = BigDecimal.valueOf(100.00);
        String start = "2026-01-01";
        String end = "2026-02-01";

        List<LessAmountResponse> expectedResponse = List.of(
            new LessAmountResponse(
                1L,
                "Test Seller",
                "test@example.com",
                amount
            )
        );

        when(
            transactionRepo.findSellersWithLessAmount(
                any(BigDecimal.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
            )
        ).thenReturn(expectedResponse);

        List<LessAmountResponse> response = analyticsService.getLessAmount(
            amount,
            start,
            end
        );

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(amount, response.get(0).getTotalAmount());
    }
}
