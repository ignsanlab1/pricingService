package com.example.pricing_service.entity;

import com.example.pricing_service.infraestructure.entity.PriceEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PriceEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test 01: Validate that all required fields in PriceEntity cannot be null.
     */
    @Test
    @Order(1)
    public void test01PriceEntityValidation() {
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setBrandId(null);
        priceEntity.setProductId(null);
        priceEntity.setPriceList(null);
        priceEntity.setStartDate(null);
        priceEntity.setEndDate(null);
        priceEntity.setPriority(null);
        priceEntity.setPrice(null);
        priceEntity.setCurrency(null);

        Set<ConstraintViolation<PriceEntity>> violations = validator.validate(priceEntity);

        assertEquals(9, violations.size());

        Map<String, Set<String>> propertyViolations = new HashMap<>();
        for (ConstraintViolation<PriceEntity> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            propertyViolations.computeIfAbsent(propertyPath, k -> new HashSet<>()).add(violation.getMessage());
        }

        assertTrue(propertyViolations.containsKey("brandId"));
        assertTrue(propertyViolations.get("brandId").contains("Brand ID cannot be null"));

        assertTrue(propertyViolations.containsKey("productId"));
        assertTrue(propertyViolations.get("productId").contains("Product ID cannot be null"));

        assertTrue(propertyViolations.containsKey("priceList"));
        assertTrue(propertyViolations.get("priceList").contains("Price list cannot be null"));

        assertTrue(propertyViolations.containsKey("startDate"));
        assertTrue(propertyViolations.get("startDate").contains("Start date cannot be null"));

        assertTrue(propertyViolations.containsKey("endDate"));
        assertTrue(propertyViolations.get("endDate").contains("End date cannot be null"));

        assertTrue(propertyViolations.containsKey("priority"));
        assertTrue(propertyViolations.get("priority").contains("Priority cannot be null"));

        assertTrue(propertyViolations.containsKey("price"));
        assertTrue(propertyViolations.get("price").contains("Price cannot be null"));

        assertTrue(propertyViolations.containsKey("currency"));
        assertTrue(propertyViolations.get("currency").contains("Currency cannot be null"));
    }

    /**
     * Test 02: Validate that a PriceEntity with all valid fields does not produce any validation errors.
     */
    @Test
    @Order(2)
    public void test02ValidPriceEntity() {
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(1L)
                .priceList(100)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .priority(1)
                .price(10.0)
                .currency("USD")
                .build();

        Set<ConstraintViolation<PriceEntity>> violations = validator.validate(priceEntity);
        assertTrue(violations.isEmpty());
    }

    /**
     * Test 03: Validate that endDate cannot be before startDate.
     */
    @Test
    @Order(3)
    public void test03InvalidDateRange() {
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(1L)
                .priceList(100)
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now())
                .priority(1)
                .price(10.0)
                .currency("USD")
                .build();

        Set<ConstraintViolation<PriceEntity>> violations = validator.validate(priceEntity);
        assertEquals(1, violations.size());
        assertEquals("End date must be after start date", violations.iterator().next().getMessage());
    }

    /**
     * Test 04: Validate that the currency must have exactly 3 characters.
     */
    @Test
    @Order(4)
    public void test04InvalidCurrencyCode() {
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(1L)
                .priceList(100)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .priority(1)
                .price(10.0)
                .currency("US")
                .build();

        Set<ConstraintViolation<PriceEntity>> violations = validator.validate(priceEntity);
        assertEquals(1, violations.size());
        assertEquals("Currency must be a 3-letter ISO code", violations.iterator().next().getMessage());
    }

    /**
     * Test 05: Validate price field when it's set to zero.
     */
    @Test
    @Order(5)
    public void test05PriceZeroValidation() {
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(1L)
                .priceList(100)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .priority(1)
                .price(0.0)
                .currency("USD")
                .build();

        Set<ConstraintViolation<PriceEntity>> violations = validator.validate(priceEntity);
        assertEquals(1, violations.size());
        assertEquals("Price must be greater than 0", violations.iterator().next().getMessage());
    }

    /**
     * Test 06: Validate negative values for priceList.
     */
    @Test
    @Order(6)
    public void test06NegativePriceListValidation() {
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(1L)
                .priceList(-10)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .priority(1)
                .price(10.0)
                .currency("USD")
                .build();

        Set<ConstraintViolation<PriceEntity>> violations = validator.validate(priceEntity);
        assertEquals(1, violations.size());
        assertEquals("Price list must be greater than or equal to 0", violations.iterator().next().getMessage());
    }

    /**
     * Test 07: Validate negative values for priority.
     */
    @Test
    @Order(7)
    public void test07NegativePriorityValidation() {
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(1L)
                .priceList(100)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .priority(-1)
                .price(10.0)
                .currency("USD")
                .build();

        Set<ConstraintViolation<PriceEntity>> violations = validator.validate(priceEntity);
        assertEquals(1, violations.size());
        assertEquals("Priority must be greater than or equal to 0", violations.iterator().next().getMessage());
    }
}
