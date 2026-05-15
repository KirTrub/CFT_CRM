package ru.trubachev.cft_crm.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.trubachev.cft_crm.dto.analytics.LessAmountResponse;
import ru.trubachev.cft_crm.dto.analytics.TopSellerResponse;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.models.Transaction;

@DataJpaTest
@ActiveProfiles("test")
class TransactionRepoTest {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private SellerRepo sellerRepo;

    private Seller seller1;
    private Seller seller2;

    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void setup() {
        transactionRepo.deleteAll();
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
        transactionRepo.save(transaction1);
        transactionRepo.save(transaction2);
    }

    @Test
    void findAllTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        assertEquals(2, transactions.size());
        assertEquals("Test Seller1", transactions.get(0).getSeller().getName());
        assertEquals("Test Seller2", transactions.get(1).getSeller().getName());
    }

    @Test
    void findTransactionById() {
        Transaction transaction = transactionRepo
            .findById(transaction1.getId())
            .orElse(null);
        assertNotNull(transaction);
        assertEquals("Test Seller1", transaction.getSeller().getName());
    }

    @Test
    void findTransactionById_NotFound() {
        Transaction transaction = transactionRepo.findById(3L).orElse(null);
        assertNull(transaction);
    }

    @Test
    void findTransactionsBySeller_Id() {
        List<Transaction> transactions =
            transactionRepo.findTransactionsBySeller_Id(seller1.getId());
        assertEquals(1, transactions.size());
        assertEquals("Test Seller1", transactions.get(0).getSeller().getName());
    }

    @Test
    void findTopSellerByPeriod() {
        TopSellerResponse topSeller = transactionRepo.findTopSellerByPeriod(
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now()
        );
        assertNotNull(topSeller);
        assertEquals(seller2.getId(), topSeller.sellerId);
        assertEquals("Test Seller2", topSeller.name);
        assertEquals("test2@example.com", topSeller.contactInfo);
    }

    @Test
    void findSellersWithLessAmount() {
        List<LessAmountResponse> sellers =
            transactionRepo.findSellersWithLessAmount(
                BigDecimal.valueOf(300.00),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
            );
        assertNotNull(sellers);
        assertEquals(1, sellers.size());
        assertEquals("Test Seller1", sellers.get(0).getName());
    }
}
