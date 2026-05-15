package ru.trubachev.cft_crm.repo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.trubachev.cft_crm.dto.analytics.LessAmountResponse;
import ru.trubachev.cft_crm.dto.analytics.TopSellerResponse;
import ru.trubachev.cft_crm.models.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsBySeller_Id(long sellerId);

    @Query(
        "SELECT t.seller.id as sellerId, t.seller.name as name, t.seller.contactInfo as contactInfo, SUM(t.amount) as totalAmount " +
            "FROM Transaction t " +
            "WHERE t.transactionDate BETWEEN :start AND :end " +
            "GROUP BY t.seller.id, t.seller.name, t.seller.contactInfo " +
            "ORDER BY SUM(t.amount) DESC LIMIT 1"
    )
    TopSellerResponse findTopSellerByPeriod(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );

    @Query(
        "SELECT t.seller.id as sellerId, t.seller.name as name, t.seller.contactInfo as contactInfo, SUM(t.amount) as totalAmount " +
            "FROM Transaction t " +
            "WHERE t.transactionDate BETWEEN :start AND :end " +
            "GROUP BY t.seller.id, t.seller.name, t.seller.contactInfo " +
            "HAVING SUM(t.amount) < :amount " +
            "ORDER BY SUM(t.amount) DESC"
    )
    List<LessAmountResponse> findSellersWithLessAmount(
        @Param("amount") BigDecimal amount,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
