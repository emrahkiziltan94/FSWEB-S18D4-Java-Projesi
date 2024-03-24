package com.workintech.s18d4.repository;

import com.workintech.s18d4.entity.Address;
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
 class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    private Address sampleAddress;

    @BeforeEach
    void setup() {
        
        sampleAddress = new Address();
        sampleAddress.setStreet("Main Street");
        sampleAddress.setNo(42);
        sampleAddress.setCity("Sample City");
        sampleAddress.setCountry("Sample Country");
        sampleAddress.setDescription("Near the park");
        entityManager.persistFlushFind(sampleAddress);
    }

    @Test
    void testSaveAndFindById() {
        
        Optional<Address> foundAddress = addressRepository.findById(sampleAddress.getId());
        assertTrue(foundAddress.isPresent());
        assertEquals("Main Street", foundAddress.get().getStreet());
    }

    @Test
    void testUpdateAddress() {
        
        sampleAddress.setCity("Updated City");
        addressRepository.save(sampleAddress);
        Optional<Address> updatedAddress = addressRepository.findById(sampleAddress.getId());
        assertTrue(updatedAddress.isPresent());
        assertEquals("Updated City", updatedAddress.get().getCity());
    }

    @Test
    void testDeleteAddress() {
        
        addressRepository.delete(sampleAddress);
        Optional<Address> deletedAddress = addressRepository.findById(sampleAddress.getId());
        assertTrue(deletedAddress.isEmpty());
    }
}
