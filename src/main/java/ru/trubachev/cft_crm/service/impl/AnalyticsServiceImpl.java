package ru.trubachev.cft_crm.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trubachev.cft_crm.dto.analytics.LessAmountResponse;
import ru.trubachev.cft_crm.dto.analytics.TopSellerResponse;
import ru.trubachev.cft_crm.exception.NoDataFoundException;
import ru.trubachev.cft_crm.repo.TransactionRepo;
import ru.trubachev.cft_crm.service.AnalyticsService;

@Service
@Transactional(readOnly = true)
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private TransactionRepo repo;

    public TopSellerResponse getTopSeller(
        String period,
        String date,
        int quarter
    ) {
        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");

        LocalDateTime start;
        LocalDateTime end;

        switch (period) {
            case "day":
                LocalDate parsedDate = LocalDate.parse(date, dayFormat);
                start = parsedDate.atStartOfDay();
                end = parsedDate.plusDays(1).atStartOfDay();
                break;
            case "month":
                YearMonth parsedMonth = YearMonth.parse(date, monthFormat);
                start = parsedMonth.atDay(1).atStartOfDay();
                end = parsedMonth.plusMonths(1).atDay(1).atStartOfDay();
                break;
            case "quarter":
                Year parsedYear = Year.parse(date, yearFormat);
                start = parsedYear
                    .atMonth(1)
                    .atDay(1)
                    .plusMonths(3L * (quarter - 1))
                    .atStartOfDay();
                end = parsedYear
                    .atMonth(1)
                    .atDay(1)
                    .plusMonths(3L * quarter)
                    .atStartOfDay();
                break;
            case "year":
                Year year = Year.parse(date, yearFormat);
                start = year.atMonth(1).atDay(1).atStartOfDay();
                end = year.plusYears(1).atMonth(1).atDay(1).atStartOfDay();
                break;
            default:
                throw new IllegalArgumentException(
                    "Invalid period: " +
                        period +
                        ". Valid values: day, month, quarter, year"
                );
        }

        TopSellerResponse results = repo.findTopSellerByPeriod(start, end);

        if (results == null) {
            throw new NoDataFoundException(
                "No transactions found for the specified period"
            );
        }

        return new TopSellerResponse(
            results.sellerId,
            results.name,
            results.contactInfo,
            results.totalAmount,
            period
        );
    }

    public List<LessAmountResponse> getLessAmount(
        BigDecimal amount,
        String start,
        String end
    ) {
        LocalDateTime startDate = LocalDate.parse(start).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(end).atTime(LocalTime.MAX);

        List<LessAmountResponse> results = repo.findSellersWithLessAmount(
            amount,
            startDate,
            endDate
        );

        return results;
    }
}
