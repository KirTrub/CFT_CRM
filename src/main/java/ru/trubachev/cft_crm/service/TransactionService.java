package ru.trubachev.cft_crm.service;

import java.util.List;
import ru.trubachev.cft_crm.dto.transaction.CreateTransaction;
import ru.trubachev.cft_crm.dto.transaction.TransactionResponse;

public interface TransactionService {
    List<TransactionResponse> getAllTransactions();
    TransactionResponse getTransactionById(long id);
    TransactionResponse addTransaction(CreateTransaction request);
    List<TransactionResponse> getSellerTransactions(long sellerId);
}
