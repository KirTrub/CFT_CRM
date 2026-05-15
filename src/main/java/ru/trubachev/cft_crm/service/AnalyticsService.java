package ru.trubachev.cft_crm.service;

import java.math.BigDecimal;
import java.util.List;
import ru.trubachev.cft_crm.dto.analytics.LessAmountResponse;
import ru.trubachev.cft_crm.dto.analytics.TopSellerResponse;

public interface AnalyticsService {
    public TopSellerResponse getTopSeller(
        String period,
        String date,
        int quarter
    );

    public List<LessAmountResponse> getLessAmount(
        BigDecimal amount,
        String start,
        String end
    );
}
