package ru.trubachev.cft_crm.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trubachev.cft_crm.dto.seller.CreateSellerRequest;
import ru.trubachev.cft_crm.dto.seller.SellerResponse;
import ru.trubachev.cft_crm.dto.seller.UpdateSellerRequest;
import ru.trubachev.cft_crm.exception.ResourceNotFoundException;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.repo.SellerRepo;
import ru.trubachev.cft_crm.service.SellerService;

@Service
@Transactional(readOnly = true)
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepo repo;

    public List<SellerResponse> getAllSellers() {
        List<Seller> sellers = repo.findAll();
        return sellers
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public SellerResponse getSellerById(long id) {
        Seller seller = repo
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Seller not found with id: " + id)
            );
        return mapToResponse(seller);
    }

    @Transactional
    public SellerResponse addSeller(CreateSellerRequest request) {
        Seller newSeller = new Seller();
        newSeller.setName(request.name);
        newSeller.setContactInfo(request.contactInfo);
        newSeller.setRegistrationDate(LocalDateTime.now());
        Seller savedSeller = repo.save(newSeller);
        return mapToResponse(savedSeller);
    }

    @Transactional
    public SellerResponse updateSeller(long id, UpdateSellerRequest request) {
        Seller existingSeller = repo
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Seller not found with id: " + id)
            );
        if (request.name != null) {
            existingSeller.setName(request.name);
        }
        if (request.contactInfo != null) {
            existingSeller.setContactInfo(request.contactInfo);
        }
        Seller updatedSeller = repo.save(existingSeller);
        return mapToResponse(updatedSeller);
    }

    @Transactional
    public void deleteSeller(long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(
                "Seller not found with id: " + id
            );
        }
        repo.deleteById(id);
    }

    private SellerResponse mapToResponse(Seller seller) {
        SellerResponse response = new SellerResponse();
        response.id = seller.getId();
        response.name = seller.getName();
        response.contactInfo = seller.getContactInfo();
        response.registrationDate = seller.getRegistrationDate();
        return response;
    }
}
