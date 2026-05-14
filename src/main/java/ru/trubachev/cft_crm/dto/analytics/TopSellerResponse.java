package ru.trubachev.cft_crm.dto.analytics;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"sellerId", "name", "contactInfo", "totalAmount", "period"})
public class TopSellerResponse {
    public Long sellerId;
    public String name;
    public String contactInfo;
    public BigDecimal totalAmount;
    public String period;

    public TopSellerResponse(Long sellerId, String name, String contactInfo, BigDecimal totalAmount, String period) {
        this.sellerId = sellerId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.totalAmount = totalAmount;
        this.period = period;
    }
    public TopSellerResponse(Long sellerId, String name, String contactInfo, BigDecimal totalAmount) {
        this.sellerId = sellerId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.totalAmount = totalAmount;
    }
}
