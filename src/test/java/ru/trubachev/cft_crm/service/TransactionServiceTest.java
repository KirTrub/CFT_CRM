package ru.trubachev.cft_crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.trubachev.cft_crm.dto.transaction.CreateTransaction;
import ru.trubachev.cft_crm.dto.transaction.TransactionResponse;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.models.Transaction;
import ru.trubachev.cft_crm.repo.SellerRepo;
import ru.trubachev.cft_crm.repo.TransactionRepo;
import ru.trubachev.cft_crm.service.impl.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private SellerRepo sellerRepo;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void addTransaction_shouldAddTransaction() {
        Seller testSeller = new Seller();
        testSeller.setId(1L);
        testSeller.setName("TestName1");
        testSeller.setContactInfo("TestContactInfo1");

        when(sellerRepo.findById(1L)).thenReturn(Optional.of(testSeller));

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setSeller(testSeller);
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setPaymentType("CASH");

        when(transactionRepo.save(any(Transaction.class))).thenReturn(
            transaction
        );

        CreateTransaction request = new CreateTransaction();
        request.sellerId = 1L;
        request.amount = BigDecimal.valueOf(100.00);
        request.paymentType = "CASH";

        TransactionResponse trResponse = transactionService.addTransaction(
            request
        );

        assertNotNull(trResponse);
        assertEquals(1L, trResponse.getSellerId());
        assertEquals(BigDecimal.valueOf(100.00), trResponse.getAmount());
        assertEquals("CASH", trResponse.getPaymentType());

        verify(transactionRepo).save(any(Transaction.class));
    }

    @Test
    void getTransactionById_shouldReturnTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        when(transactionRepo.findById(1L)).thenReturn(Optional.of(transaction));

        TransactionResponse response = transactionService.getTransactionById(
            1L
        );
        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(transactionRepo).findById(1L);
    }

    @Test
    void getSellerTransactions_shouldReturnTransactionResponses() {
        Seller testSeller = new Seller();
        testSeller.setId(1L);
        testSeller.setName("TestName1");
        testSeller.setContactInfo("TestContactInfo1");

        when(sellerRepo.existsById(1L)).thenReturn(true);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(
            new Transaction() {
                {
                    setId(1L);
                    setSeller(testSeller);
                    setAmount(BigDecimal.valueOf(100.00));
                    setPaymentType("CASH");
                    setTransactionDate(null);
                }
            }
        );

        when(transactionRepo.findTransactionsBySeller_Id(1L)).thenReturn(
            transactions
        );

        List<TransactionResponse> responses =
            transactionService.getSellerTransactions(1L);
        assertNotNull(responses);
        assertEquals(1L, responses.size());
        verify(transactionRepo).findTransactionsBySeller_Id(1L);
    }
}
