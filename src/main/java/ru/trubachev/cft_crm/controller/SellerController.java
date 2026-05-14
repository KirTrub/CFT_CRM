package ru.trubachev.cft_crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.trubachev.cft_crm.dto.seller.CreateSellerRequest;
import ru.trubachev.cft_crm.dto.seller.SellerResponse;
import ru.trubachev.cft_crm.dto.seller.UpdateSellerRequest;
import ru.trubachev.cft_crm.dto.transaction.TransactionResponse;
import ru.trubachev.cft_crm.service.SellerService;
import ru.trubachev.cft_crm.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    private SellerService service;
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<SellerResponse> getAllSellers(){
        List<SellerResponse> response = service.getAllSellers();
        return response;
    }

    @GetMapping("/{id}")
    public SellerResponse getSellerById(@PathVariable long id){
        return service.getSellerById(id);
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public SellerResponse addSeller(@Valid @RequestBody CreateSellerRequest request){
        return service.addSeller(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public SellerResponse updateSeller(@PathVariable long id, @Valid @RequestBody UpdateSellerRequest request){
        return service.updateSeller(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteSeller(@PathVariable long id){
        service.deleteSeller(id);
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionResponse> getSellerTransactions(@PathVariable long id){
        return transactionService.getSellerTransactions(id);
    }

}
