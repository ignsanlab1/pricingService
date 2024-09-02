package com.example.pricing_service.controller;

import com.example.pricing_service.infraestructure.commons.exceptions.PriceBadRequestException;
import com.example.pricing_service.infraestructure.commons.exceptions.PriceNotFoundException;
import com.example.pricing_service.infraestructure.rest.controller.PriceControllerImpl;
import com.example.pricing_service.domain.dto.request.PriceRequest;
import com.example.pricing_service.application.service.PriceService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static com.example.pricing_service.infraestructure.commons.constants.ExceptionMessages.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(PriceControllerImpl.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        when(priceService.getApplicablePrice(any(Long.class), any(Long.class), any(LocalDateTime.class)))
                .thenAnswer(invocation -> {
                    Long productId = invocation.getArgument(0);
                    Long brandId = invocation.getArgument(1);
                    LocalDateTime applicationDate = invocation.getArgument(2);

                    if (applicationDate.equals(LocalDateTime.of(2020, 6, 14, 10, 0))) {
                        return new PriceRequest(productId, brandId, 1, LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59), 35.50, "EUR");
                    } else if (applicationDate.equals(LocalDateTime.of(2020, 6, 14, 16, 0))) {
                        return new PriceRequest(productId, brandId, 2, LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59), 25.45, "EUR");
                    } else if (applicationDate.equals(LocalDateTime.of(2020, 6, 14, 21, 0))) {
                        return new PriceRequest(productId, brandId, 1, LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59), 35.50, "EUR");
                    } else if (applicationDate.equals(LocalDateTime.of(2020, 6, 15, 10, 0))) {
                        return new PriceRequest(productId, brandId, 3, LocalDateTime.of(2020, 6, 15, 0, 0),
                                LocalDateTime.of(2020, 6, 15, 11, 0), 30.50, "EUR");
                    } else if (applicationDate.equals(LocalDateTime.of(2020, 6, 16, 21, 0))) {
                        return new PriceRequest(productId, brandId, 4, LocalDateTime.of(2020, 6, 16, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59), 38.95, "EUR");
                    } else if (applicationDate.equals(LocalDateTime.of(2020, 6, 17, 10, 0))) {
                        throw new PriceNotFoundException(PRICE_NOT_FOUND);
                    } else {
                        throw new PriceBadRequestException(PRICE_BAD_REQUEST);
                    }
                });
    }

    /**
     * Test 01: Makes a request at 10:00 on June 14th for product 35455 and brand 1 (ZARA)
     * Verifies that the response status is OK and the price is 35.50 EUR.
     */
    @Test
    @Order(1)
    void test01GetApplicablePriceAt10OnJune14() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EUR"));
    }

    /**
     * Test 02: Makes a request at 16:00 on June 14th for product 35455 and brand 1 (ZARA)
     * Verifies that the response status is OK and the price is 35.50 EUR.
     */
    @Test
    @Order(2)
    void test02GetApplicablePriceAt16OnJune14() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(25.45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EUR"));
    }

    /**
     * Test 03: Makes a request at 21:00 on June 14th for product 35455 and brand 1 (ZARA)
     * Verifies that the response status is OK and the price is 35.50 EUR.
     */
    @Test
    @Order(3)
    void test03GetApplicablePriceAt21OnJune14() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EUR"));
    }

    /**
     * Test 04: Makes a request at 10:00 on June 15th for product 35455 and brand 1 (ZARA)
     * Verifies that the response status is OK and the price is 30.50 EUR.
     */
    @Test
    @Order(4)
    void test04GetApplicablePriceAt10OnJune15() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EUR"));
    }

    /**
     * Test 05: Makes a request at 21:00 on June 16th for product 35455 and brand 1 (ZARA)
     * Verifies that the response status is OK and the price is 38.95 EUR.
     */
    @Test
    @Order(5)
    void test05GetApplicablePriceAt21OnJune16() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(38.95))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EUR"));
    }

    /**
     * Test 06: Request with bad parameters - Bad Request
     * Verifies that a Bad Request exception is thrown when providing incorrect parameters.
     */
    @Test
    @Order(6)
    void test06GetPriceInvalidInputParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices")
                        .param("productId", "-1")
                        .param("brandId", "-1")
                        .param("applicationDate", "2020-06-18T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test 07: Request for non-existent product - Not Found
     * Verifies that a Not Found exception is thrown when the requested product is not found.
     */
    @Test
    @Order(7)
    void test07GetPriceNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-17T10:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}