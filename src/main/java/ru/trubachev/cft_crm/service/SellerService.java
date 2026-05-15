package ru.trubachev.cft_crm.service;

import java.util.List;
import ru.trubachev.cft_crm.dto.seller.CreateSellerRequest;
import ru.trubachev.cft_crm.dto.seller.SellerResponse;
import ru.trubachev.cft_crm.dto.seller.UpdateSellerRequest;

public interface SellerService {
    List<SellerResponse> getAllSellers();
    SellerResponse getSellerById(long id);
    SellerResponse addSeller(CreateSellerRequest request);
    SellerResponse updateSeller(long id, UpdateSellerRequest request);
    void deleteSeller(long id);
}
