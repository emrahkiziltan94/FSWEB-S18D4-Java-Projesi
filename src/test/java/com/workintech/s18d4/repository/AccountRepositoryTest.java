package com.workintech.s18d4.repository;

import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    private Account sampleAccount;

    @BeforeEach
    void setup() {
        
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer = entityManager.persistFlushFind(customer);

        
        sampleAccount = new Account();
        sampleAccount.setAccountName("Savings Account");
        sampleAccount.setMoneyAmount(1000.00);
        sampleAccount.setCustomer(customer);
        entityManager.persistFlushFind(sampleAccount);
    }

    @Test
    void testSaveAndFindById() {
        
        Optional<Account> foundAccount = accountRepository.findById(sampleAccount.getId());
        assertTrue(foundAccount.isPresent());
        assertEquals(sampleAccount.getAccountName(), foundAccount.get().getAccountName());
    }

    @Test
    void testUpdateAccount() {
        
        sampleAccount.setMoneyAmount(2000.00);
        accountRepository.save(sampleAccount);
        Optional<Account> updatedAccount = accountRepository.findById(sampleAccount.getId());
        assertTrue(updatedAccount.isPresent());
        assertEquals(2000.00, updatedAccount.get().getMoneyAmount());
    }

    @Test
    void testDeleteAccount() {
        
        accountRepository.delete(sampleAccount);
        Optional<Account> deletedAccount = accountRepository.findById(sampleAccount.getId());
        assertTrue(deletedAccount.isEmpty());
    }
}

