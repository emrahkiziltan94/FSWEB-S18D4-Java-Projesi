package com.workintech.s18d4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        sampleCustomer = new Customer();
        sampleCustomer.setId(1L);
        sampleCustomer.setEmail("customer@example.com");
        sampleCustomer.setSalary(5000.00);
    }

    @Test
    void testSaveCustomer() throws Exception {
        given(customerService.save(any())).willReturn(sampleCustomer);

        CustomerResponse expectedResponse = new CustomerResponse(sampleCustomer.getId(), sampleCustomer.getEmail(), sampleCustomer.getSalary());

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) expectedResponse.id())))
                .andExpect(jsonPath("$.email", is(expectedResponse.email())))
                .andExpect(jsonPath("$.salary", is(expectedResponse.salary())));

        verify(customerService).save(any());
    }


}
