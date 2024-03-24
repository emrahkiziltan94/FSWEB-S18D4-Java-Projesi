package com.workintech.s18d4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private CustomerService customerService;

    private Account sampleAccount;
    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        sampleCustomer = new Customer();
        sampleCustomer.setId(1L);
        sampleCustomer.setEmail("customer@example.com");
        sampleCustomer.setSalary(5000.00);

        sampleAccount = new Account();
        sampleAccount.setId(1L);
        sampleAccount.setAccountName("Savings Account");
        sampleAccount.setMoneyAmount(1000.00);
        sampleAccount.setCustomer(sampleCustomer);

        // Use an ArrayList to allow modifications
        List<Account> modifiableAccountsList = new ArrayList<>();
        modifiableAccountsList.add(sampleAccount);
        sampleCustomer.setAccounts(modifiableAccountsList);
    }


    @Test
    void testFindAll() throws Exception {
        when(accountService.findAll()).thenReturn(List.of(sampleAccount));

        mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is((int) sampleAccount.getId())))
                .andExpect(jsonPath("$[0].accountName", is(sampleAccount.getAccountName())));

        verify(accountService).findAll();
    }

    @Test
    void testFind() throws Exception {
        when(accountService.find(sampleAccount.getId())).thenReturn(sampleAccount);

        mockMvc.perform(get("/account/{id}", sampleAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is((int) sampleAccount.getId())))
                .andExpect(jsonPath("$.accountName", is(sampleAccount.getAccountName())));

        verify(accountService).find(sampleAccount.getId());
    }

    @Test
    void testSave() throws Exception {
        when(customerService.find(sampleCustomer.getId())).thenReturn(sampleCustomer);
        when(accountService.save(any())).thenReturn(sampleAccount);

        mockMvc.perform(post("/account/{customerId}", sampleCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAccount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) sampleAccount.getId())))
                .andExpect(jsonPath("$.accountName", is(sampleAccount.getAccountName())));

        verify(customerService).find(sampleCustomer.getId());
        verify(accountService).save(any());
    }

    @Test
    void testUpdateAccount() throws Exception {
        long customerId = sampleCustomer.getId();
        Account updatedAccount = new Account();
        updatedAccount.setId(sampleAccount.getId());
        updatedAccount.setAccountName("Updated Account");
        updatedAccount.setMoneyAmount(2000.00);
        updatedAccount.setCustomer(sampleCustomer);

        // Ensure the customer is associated with the account to be updated
        List<Account> accounts = new ArrayList<>();
        accounts.add(sampleAccount);
        sampleCustomer.setAccounts(accounts);

        when(customerService.find(customerId)).thenReturn(sampleCustomer);
        when(accountService.find(sampleAccount.getId())).thenReturn(sampleAccount);
        when(accountService.save(any())).thenReturn(updatedAccount);

        mockMvc.perform(put("/account/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAccount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) updatedAccount.getId())))
                .andExpect(jsonPath("$.accountName", is(updatedAccount.getAccountName())))
                .andExpect(jsonPath("$.moneyAmount", is(updatedAccount.getMoneyAmount())));

        verify(customerService).find(customerId);
        given(customerService.save(any())).willReturn(sampleCustomer);
    }


    @Test
    void testRemoveAccount() throws Exception {
        when(accountService.find(sampleAccount.getId())).thenReturn(sampleAccount);
        when(accountService.delete(sampleAccount.getId())).thenReturn(sampleAccount);

        mockMvc.perform(delete("/account/{id}", sampleAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) sampleAccount.getId())))
                .andExpect(jsonPath("$.accountName", is(sampleAccount.getAccountName())))
                .andExpect(jsonPath("$.moneyAmount", is(sampleAccount.getMoneyAmount())));

        verify(accountService).find(sampleAccount.getId());
        verify(accountService).delete(sampleAccount.getId());
    }


}

