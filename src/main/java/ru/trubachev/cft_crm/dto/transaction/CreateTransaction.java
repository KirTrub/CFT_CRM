package ru.trubachev.cft_crm.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public class CreateTransaction {
    @NotNull(message = "Seller ID is required")
    public Long sellerId;

    @Positive(message = "Amount must be positive")
    public BigDecimal amount;

    @NotBlank(message = "Payment type is required")
    @Pattern(regexp = "CASH|CARD|TRANSFER", message = "paymentType must be CASH, CARD, or TRANSFER")
    public String paymentType;
}
