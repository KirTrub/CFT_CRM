package ru.trubachev.cft_crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.trubachev.cft_crm.dto.seller.*;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.repo.SellerRepo;
import ru.trubachev.cft_crm.service.impl.SellerServiceImpl;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {

    @Mock
    private SellerRepo sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Test
    void addSeller_first_shouldReturnSellerResponse() {
        Seller testSeller = new Seller();
        testSeller.setId(1L);
        testSeller.setName("TestName1");
        testSeller.setContactInfo("TestContactInfo1");

        when(sellerRepository.save(any(Seller.class))).thenReturn(testSeller);

        CreateSellerRequest request = new CreateSellerRequest();
        request.name = "TestName1";
        request.contactInfo = "TestContactInfo1";

        SellerResponse response = sellerService.addSeller(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("TestName1", response.getName());
        assertEquals("TestContactInfo1", response.getContactInfo());

        verify(sellerRepository).save(any(Seller.class));
    }

    @Test
    void addSeller_second_shouldReturnSellerResponse() {
        Seller testSeller = new Seller();
        testSeller.setId(2L);
        testSeller.setName("TestName2");
        testSeller.setContactInfo("TestContactInfo2");
        when(sellerRepository.save(any(Seller.class))).thenReturn(testSeller);

        CreateSellerRequest request = new CreateSellerRequest();
        request.name = "TestName2";
        request.contactInfo = "TestContactInfo2";

        SellerResponse response = sellerService.addSeller(request);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("TestName2", response.getName());
        assertEquals("TestContactInfo2", response.getContactInfo());
        verify(sellerRepository, times(1)).save(any(Seller.class));
    }

    @Test
    void getSellerById_shouldReturnSellerResponse() {
        Long sellerId = 1L;

        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setName("TestName1");
        seller.setContactInfo("TestContactInfo1");

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        SellerResponse response = sellerService.getSellerById(sellerId);

        assertNotNull(response);
        assertEquals(sellerId, response.getId());
        verify(sellerRepository).findById(1L);
    }

    @Test
    void getAllSellers_shouldReturnListOfSellerResponses() {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("TestName1");
        seller1.setContactInfo("TestContactInfo1");

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("TestName2");
        seller2.setContactInfo("TestContactInfo2");

        when(sellerRepository.findAll()).thenReturn(List.of(seller1, seller2));
        List<SellerResponse> responses = sellerService.getAllSellers();

        assertNotNull(responses);
        assertEquals(2, responses.size());
    }

    @Test
    void updateSeller_shouldReturnUpdatedSellerResponse() {
        Long sellerId = 1L;

        UpdateSellerRequest request = new UpdateSellerRequest();
        request.name = "UpdatedName";
        request.contactInfo = "UpdatedContactInfo";

        Seller existingSeller = new Seller();
        existingSeller.setId(sellerId);
        existingSeller.setName("OldName");
        existingSeller.setContactInfo("OldContact");

        Seller updatedSeller = new Seller();
        updatedSeller.setId(sellerId);
        updatedSeller.setName("UpdatedName");
        updatedSeller.setContactInfo("UpdatedContactInfo");

        when(sellerRepository.findById(sellerId)).thenReturn(
            Optional.of(existingSeller)
        );

        when(sellerRepository.save(any(Seller.class))).thenReturn(
            updatedSeller
        );

        SellerResponse response = sellerService.updateSeller(sellerId, request);

        assertNotNull(response);
        assertEquals(sellerId, response.getId());
        assertEquals("UpdatedName", response.getName());
        assertEquals("UpdatedContactInfo", response.getContactInfo());

        verify(sellerRepository).findById(sellerId);
        verify(sellerRepository).save(any(Seller.class));
    }
}
