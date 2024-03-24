package com.workintech.s18d4.service;


import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account();
        account.setId(1L);
        account.setAccountName("Savings Account");
        account.setMoneyAmount(1000.00);
    }

    @Test
    void testFindAll() {
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));
        List<Account> result = accountService.findAll();
        assertEquals(1, result.size());
        assertEquals(account, result.get(0));
    }

    @Test
    void testFind() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Account result = accountService.find(1L);
        assertEquals(account, result);
    }

    @Test
    void testSave() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Account savedAccount = accountService.save(account);
        assertEquals(account, savedAccount);
    }

    @Test
    void testDelete() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        doNothing().when(accountRepository).delete(account);
        accountService.delete(1L);
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void testDeleteNotFound() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(accountService.delete(1L));
    }


}
