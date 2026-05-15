package ru.trubachev.cft_crm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.trubachev.cft_crm.dto.seller.CreateSellerRequest;
import ru.trubachev.cft_crm.dto.seller.SellerResponse;
import ru.trubachev.cft_crm.dto.seller.UpdateSellerRequest;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.service.SellerService;
import ru.trubachev.cft_crm.service.TransactionService;

@WebMvcTest(SellerController.class)
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SellerService sellerService;

    @MockitoBean
    private TransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private String asJsonString(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void testGetAllSellers() throws Exception {
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.name = "Test Seller";
        sellerResponse.contactInfo = "test@example.com";

        when(sellerService.getAllSellers()).thenReturn(List.of(sellerResponse));

        mockMvc
            .perform(MockMvcRequestBuilders.get("/sellers"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetSellerById() throws Exception {
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.name = "Test Seller";
        sellerResponse.contactInfo = "test@example.com";

        when(sellerService.getSellerById(1L)).thenReturn(sellerResponse);

        mockMvc
            .perform(MockMvcRequestBuilders.get("/sellers/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddSeller() throws Exception {
        CreateSellerRequest sellerRequest = new CreateSellerRequest();
        sellerRequest.name = "New Seller";
        sellerRequest.contactInfo = "new@example.com";

        String content = asJsonString(sellerRequest);
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/sellers")
                    .contentType(MediaType.parseMediaType("application/json"))
                    .content(content)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateSeller() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Test Seller");
        seller.setContactInfo("test@example.com");

        UpdateSellerRequest updateRequest = new UpdateSellerRequest();
        updateRequest.name = "Updated Seller";
        updateRequest.contactInfo = "updated@example.com";

        SellerResponse updatedSeller = new SellerResponse();
        updatedSeller.id = seller.getId();
        updatedSeller.name = updateRequest.name;
        updatedSeller.contactInfo = updateRequest.contactInfo;

        when(
            sellerService.updateSeller(eq(1L), any(UpdateSellerRequest.class))
        ).thenReturn(updatedSeller);

        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/sellers/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(updateRequest))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(updatedSeller.id)
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(
                    updatedSeller.name
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.contactInfo").value(
                    updatedSeller.contactInfo
                )
            );
    }
}
