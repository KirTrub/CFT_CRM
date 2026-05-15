package ru.trubachev.cft_crm.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.trubachev.cft_crm.models.Seller;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Long> {
    List<Seller> findAll();
}
