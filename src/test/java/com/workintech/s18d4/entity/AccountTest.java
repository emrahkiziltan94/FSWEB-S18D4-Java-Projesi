package com.workintech.s18d4.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private Account account;
    private Customer customer;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);
        account.setAccountName("Savings");
        account.setMoneyAmount(1500.0);

        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        account.setCustomer(customer);
    }

    @Test
    void testAccountProperties() {
        assertEquals(1L, account.getId());
        assertEquals("Savings", account.getAccountName());
        assertEquals(1500.0, account.getMoneyAmount(), 0.001);
        assertEquals(customer, account.getCustomer());
        assertEquals("John", account.getCustomer().getFirstName());
    }
}
