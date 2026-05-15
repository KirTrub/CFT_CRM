package ru.trubachev.cft_crm.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.models.Transaction;

@DataJpaTest
@ActiveProfiles("test")
class SellerRepoTest {

    @Autowired
    private SellerRepo sellerRepo;

    private Seller seller1;
    private Seller seller2;

    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void setup() {
        sellerRepo.deleteAll();
        seller1 = new Seller(
            "Test Seller1",
            "test1@example.com",
            LocalDateTime.now()
        );

        seller2 = new Seller(
            "Test Seller2",
            "test2@example.com",
            LocalDateTime.now()
        );

        transaction1 = new Transaction(
            seller1,
            BigDecimal.valueOf(100.00),
            "CASH",
            LocalDateTime.now()
        );

        transaction2 = new Transaction(
            seller2,
            BigDecimal.valueOf(400.00),
            "CASH",
            LocalDateTime.now()
        );

        sellerRepo.save(seller1);
        sellerRepo.save(seller2);
    }

    @Test
    void testFindCreatedSeller() {
        Seller seller = sellerRepo.findById(seller1.getId()).orElse(null);
        assertNotNull(seller);
        assertEquals("Test Seller1", seller.getName());
        assertEquals("test1@example.com", seller.getContactInfo());
    }

    @Test
    void testFindAll() {
        List<Seller> sellers = sellerRepo.findAll();
        assertEquals(2, sellers.size());
        assertEquals("Test Seller1", sellers.get(0).getName());
        assertEquals("Test Seller2", sellers.get(1).getName());
    }

    @Test
    void testUpdateSeller() {
        seller1.setName("Updated Seller1");
        sellerRepo.save(seller1);
        Seller updatedSeller = sellerRepo
            .findById(seller1.getId())
            .orElse(null);
        assertNotNull(updatedSeller);
        assertEquals("Updated Seller1", updatedSeller.getName());
    }

    @Test
    void delete_shouldSoftDeleteSeller() {
        Seller seller = new Seller(
            "Test Seller",
            "test@example.com",
            LocalDateTime.now()
        );

        sellerRepo.save(seller);
        Long id = seller.getId();

        assertTrue(sellerRepo.findById(id).isPresent());

        sellerRepo.delete(seller);

        assertFalse(sellerRepo.findById(id).isPresent());
    }
}
