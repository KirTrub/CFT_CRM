package ru.trubachev.cft_crm.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.trubachev.cft_crm.dto.transaction.CreateTransaction;
import ru.trubachev.cft_crm.dto.transaction.TransactionResponse;
import ru.trubachev.cft_crm.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public List<TransactionResponse> getAllTransactions() {
        return service.getAllTransactions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public TransactionResponse getTransactionById(@PathVariable long id) {
        return service.getTransactionById(id);
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public TransactionResponse addTransaction(
        @Valid @RequestBody CreateTransaction request
    ) {
        return service.addTransaction(request);
    }
}
