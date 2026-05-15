package ru.trubachev.cft_crm.service;

import java.util.List;
import ru.trubachev.cft_crm.dto.seller.CreateSellerRequest;
import ru.trubachev.cft_crm.dto.seller.SellerResponse;
import ru.trubachev.cft_crm.dto.seller.UpdateSellerRequest;

public interface SellerService {
    public List<SellerResponse> getAllSellers();
    public SellerResponse getSellerById(long id);
    public SellerResponse addSeller(CreateSellerRequest request);
    public SellerResponse updateSeller(long id, UpdateSellerRequest request);
    public void deleteSeller(long id);
}
