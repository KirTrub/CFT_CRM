package ru.trubachev.cft_crm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.trubachev.cft_crm.models.Seller;

import java.util.List;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Long> {
}
