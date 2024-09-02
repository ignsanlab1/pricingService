package com.example.pricing_service.domain.port;

import com.example.pricing_service.domain.dto.request.PriceRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface PriceController {
    ResponseEntity<PriceRequest> getPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
