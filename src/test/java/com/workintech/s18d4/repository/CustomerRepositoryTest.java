package com.workintech.s18d4.repository;

import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer sampleCustomer;

    @BeforeEach
    void setup() {
        
        Address address = new Address();
        address.setStreet("Main Street");
        address.setNo(42);
        address.setCity("Sample City");
        address.setCountry("Sample Country");
        entityManager.persist(address);

        sampleCustomer = new Customer();
        sampleCustomer.setFirstName("John");
        sampleCustomer.setLastName("Doe");
        sampleCustomer.setEmail("john.doe@example.com");
        sampleCustomer.setSalary(50000.0);
        sampleCustomer.setAddress(address);
        entityManager.persist(sampleCustomer);
    }

    @Test
    void testSaveAndFindById() {
        
        Optional<Customer> foundCustomer = customerRepository.findById(sampleCustomer.getId());
        assertTrue(foundCustomer.isPresent());
        assertEquals("John", foundCustomer.get().getFirstName());
    }

    @Test
    void testUpdateCustomer() {
        
        sampleCustomer.setEmail("updated.email@example.com");
        customerRepository.save(sampleCustomer);
        Optional<Customer> updatedCustomer = customerRepository.findById(sampleCustomer.getId());
        assertTrue(updatedCustomer.isPresent());
        assertEquals("updated.email@example.com", updatedCustomer.get().getEmail());
    }

    @Test
    void testDeleteCustomer() {
        
        customerRepository.delete(sampleCustomer);
        Optional<Customer> deletedCustomer = customerRepository.findById(sampleCustomer.getId());
        assertFalse(deletedCustomer.isPresent());
    }
}

