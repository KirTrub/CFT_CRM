package ru.trubachev.cft_crm.service;

import java.util.List;
import ru.trubachev.cft_crm.dto.transaction.CreateTransaction;
import ru.trubachev.cft_crm.dto.transaction.TransactionResponse;

public interface TransactionService {
    public List<TransactionResponse> getAllTransactions();
    public TransactionResponse getTransactionById(long id);
    public TransactionResponse addTransaction(CreateTransaction request);
    public List<TransactionResponse> getSellerTransactions(long sellerId);
}
