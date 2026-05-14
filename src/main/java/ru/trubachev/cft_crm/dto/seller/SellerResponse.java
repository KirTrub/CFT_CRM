package ru.trubachev.cft_crm.dto.seller;

import java.time.LocalDateTime;

public class SellerResponse {
    public Long id;
    public String name;
    public String contactInfo;
    public LocalDateTime registrationDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}
