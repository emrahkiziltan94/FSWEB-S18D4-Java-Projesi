package com.workintech.s18d4.service;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.repository.CustomerRepository;
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

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
    }

    @Test
    public void testFindAll() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        List<Customer> result = customerService.findAll();
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    public void testFind() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer result = customerService.find(1L);
        assertEquals(customer, result);
    }

    @Test
    public void testSave() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = customerService.save(customer);
        assertEquals(customer, savedCustomer);
    }

    @Test
    public void testDelete() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);
        Customer deletedCustomer = customerService.delete(1L);
        assertEquals(customer, deletedCustomer);
    }

    @Test
    public void testDeleteNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(customerService.delete(1L));
    }
}
