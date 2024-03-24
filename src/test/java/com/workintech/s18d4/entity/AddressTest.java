package com.workintech.s18d4.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddressTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setId(1L);
        address.setStreet("Main Street");
        address.setNo(100);
        address.setCity("Sample City");
        address.setCountry("Sample Country");
        address.setDescription("Near the big landmark");
    }

    @Test
    void testAddressProperties() {
        assertEquals(1L, address.getId());
        assertEquals("Main Street", address.getStreet());
        assertEquals(100, address.getNo());
        assertEquals("Sample City", address.getCity());
        assertEquals("Sample Country", address.getCountry());
        assertEquals("Near the big landmark", address.getDescription());

        // Testing nullability for description
        address.setDescription(null);
        assertNull(address.getDescription());
    }

    @Test
    void testCustomerAssociation() {
        Customer customer = new Customer(); // Assuming you have a default constructor
        customer.setEmail("John Doe"); // Assuming Customer has this setter
        address.setCustomer(customer);

        assertEquals("John Doe", address.getCustomer().getEmail());
    }
}
