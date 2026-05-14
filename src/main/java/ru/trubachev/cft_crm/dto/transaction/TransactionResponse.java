package ru.trubachev.cft_crm.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    public Long id;
    public Long sellerId;
    public BigDecimal amount;
    public String paymentType;
    public LocalDateTime transactionDate;

    public Long getId() {
        return id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
