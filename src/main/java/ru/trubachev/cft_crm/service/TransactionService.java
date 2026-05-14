package ru.trubachev.cft_crm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.trubachev.cft_crm.dto.transaction.CreateTransaction;
import ru.trubachev.cft_crm.dto.transaction.TransactionResponse;
import ru.trubachev.cft_crm.exception.ResourceNotFoundException;
import ru.trubachev.cft_crm.models.Transaction;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.repo.TransactionRepo;
import ru.trubachev.cft_crm.repo.SellerRepo;

@Service
@Transactional(readOnly = true)
public class TransactionService {
    @Autowired
    TransactionRepo repo;

    @Autowired
    SellerRepo sellerRepo;

    public List<TransactionResponse> getAllTransactions(){
        List<Transaction> transactions = repo.findAll();
        return transactions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public TransactionResponse getTransactionById(long id){
        Transaction transaction = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return mapToResponse(transaction);
    }

    @Transactional
    public TransactionResponse addTransaction(CreateTransaction request){
        Seller seller = sellerRepo.findById(request.sellerId)
            .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + request.sellerId));

        Transaction newTransaction = new Transaction();
        newTransaction.setSeller(seller);
        newTransaction.setAmount(request.amount);
        newTransaction.setPaymentType(request.paymentType);
        newTransaction.setTransactionDate(java.time.LocalDateTime.now());
        Transaction savedTransaction = repo.save(newTransaction);
        return mapToResponse(savedTransaction);
    }

    public List<TransactionResponse> getSellerTransactions(long sellerId){
        if (!sellerRepo.existsById(sellerId)) {
            throw new ResourceNotFoundException("Seller not found with id: " + sellerId);
        }
        List<Transaction> transactions = repo.findTransactionsBySellerId(sellerId);
        return transactions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.id = transaction.getId();
        response.sellerId = transaction.getSellerId();
        response.amount = transaction.getAmount();
        response.paymentType = transaction.getPaymentType();
        response.transactionDate = transaction.getTransactionDate();
        return response;
    }
}
