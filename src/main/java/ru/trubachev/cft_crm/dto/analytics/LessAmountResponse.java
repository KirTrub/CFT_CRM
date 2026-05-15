package ru.trubachev.cft_crm.dto.analytics;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

@JsonPropertyOrder(
    { "sellerId", "name", "contactInfo", "totalAmount", "period" }
)
public class LessAmountResponse {

    Long id;
    String name;
    String contactInfo;
    BigDecimal totalAmount;

    public LessAmountResponse(
        Long id,
        String name,
        String contactInfo,
        BigDecimal totalAmount
    ) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
