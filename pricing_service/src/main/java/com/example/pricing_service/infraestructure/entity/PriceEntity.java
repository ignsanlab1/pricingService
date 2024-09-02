package com.example.pricing_service.infraestructure.entity;

import com.example.pricing_service.domain.validators.ValidDateRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES", schema= "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@ValidDateRange
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "BRAND_ID")
    @NotNull(message = "Brand ID cannot be null")
    private Long brandId;

    @Column(name = "PRODUCT_ID")
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @Column(name = "PRICE_LIST")
    @NotNull(message = "Price list cannot be null")
    @Min(value = 0, message = "Price list must be greater than or equal to 0")
    private Integer priceList;

    @Column(name = "START_DATE")
    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    @NotNull(message = "End date cannot be null")
    private LocalDateTime endDate;

    @Column(name = "PRIORITY")
    @NotNull(message = "Priority cannot be null")
    @Min(value = 0, message = "Priority must be greater than or equal to 0")
    private Integer priority;

    @Column(name = "PRICE")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @Column(name = "CURR")
    @NotNull(message = "Currency cannot be null")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    private String currency;
}
