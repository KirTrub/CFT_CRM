package ru.trubachev.cft_crm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "sellers")
@SQLDelete(sql = "UPDATE sellers SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonPropertyOrder({ "id", "name", "contactInfo", "registrationDate" })
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Contact info is mandatory")
    String contactInfo;

    LocalDateTime registrationDate;
    private boolean deleted = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Seller(
        String name,
        String contactInfo,
        LocalDateTime registrationDate
    ) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
    }

    public Seller() {}
}
