package ru.trubachev.cft_crm.dto.seller;

import jakarta.validation.constraints.NotBlank;

public class CreateSellerRequest {
    @NotBlank(message = "Name is required")
    public String name;

    @NotBlank(message = "Contact info is required")
    public String contactInfo;
}