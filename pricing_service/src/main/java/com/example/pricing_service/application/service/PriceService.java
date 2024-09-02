package com.example.pricing_service.application.service;

import com.example.pricing_service.domain.dto.request.PriceRequest;
import java.time.LocalDateTime;

public interface PriceService {
    PriceRequest getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
