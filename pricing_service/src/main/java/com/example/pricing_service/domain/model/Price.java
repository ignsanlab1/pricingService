package com.example.pricing_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Price {
    private Long id;
    private Long brandId;
    private Long productId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priority;
    private Double price;
    private String currency;
}
