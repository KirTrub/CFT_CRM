package ru.trubachev.cft_crm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.trubachev.cft_crm.dto.transaction.TransactionResponse;
import ru.trubachev.cft_crm.models.Seller;
import ru.trubachev.cft_crm.service.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @MockitoBean
    private TransactionService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String asJsonString(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void getAllTransactions() throws Exception {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Test Seller1");
        seller1.setContactInfo("test1@example.com");
        TransactionResponse tr1 = new TransactionResponse();
        tr1.id = 1L;
        tr1.sellerId = seller1.getId();
        tr1.amount = BigDecimal.valueOf(100.00);
        tr1.paymentType = "CASH";
        tr1.transactionDate = LocalDateTime.now();
        TransactionResponse tr2 = new TransactionResponse();
        tr2.id = 2L;
        tr2.sellerId = seller1.getId();
        tr2.amount = BigDecimal.valueOf(400.00);
        tr2.paymentType = "CASH";
        tr2.transactionDate = LocalDateTime.now();
        List<TransactionResponse> transactions = List.of(tr1, tr2);

        when(service.getAllTransactions()).thenReturn(transactions);

        mockMvc
            .perform(MockMvcRequestBuilders.get("/transactions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getTransactionById() throws Exception {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Test Seller1");
        seller1.setContactInfo("test1@example.com");
        TransactionResponse tr1 = new TransactionResponse();
        tr1.id = 1L;
        tr1.sellerId = seller1.getId();
        tr1.amount = BigDecimal.valueOf(100.00);
        tr1.paymentType = "CASH";
        tr1.transactionDate = LocalDateTime.now();

        when(service.getTransactionById(1L)).thenReturn(tr1);

        mockMvc
            .perform(MockMvcRequestBuilders.get("/transactions/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void addTransaction() throws Exception {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Test Seller1");
        seller1.setContactInfo("test1@example.com");
        TransactionResponse tr1 = new TransactionResponse();
        tr1.id = 1L;
        tr1.sellerId = seller1.getId();
        tr1.amount = BigDecimal.valueOf(100.00);
        tr1.paymentType = "CASH";
        tr1.transactionDate = LocalDateTime.now();
        when(service.addTransaction(any())).thenReturn(tr1);

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/transactions")
                    .contentType("application/json")
                    .content(Objects.requireNonNull(asJsonString(tr1)))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));
    }
}
