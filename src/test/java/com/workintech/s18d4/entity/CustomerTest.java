package com.workintech.s18d4.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {
    private Customer customer;
    private Account account;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.com");
        customer.setSalary(3000.0);

        account = new Account();
        account.setId(2L);
        account.setAccountName("Checking");
        account.setMoneyAmount(2500.0);

        customer.setAccounts(List.of(account));
        account.setCustomer(customer);
    }

    @Test
    void testCustomerProperties() {
        assertEquals(2L, customer.getId());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("jane.doe@example.com", customer.getEmail());
        assertEquals(3000.0, customer.getSalary(), 0.001);
        assertTrue(customer.getAccounts().contains(account));
        assertEquals(customer, customer.getAccounts().get(0).getCustomer());
    }
}
