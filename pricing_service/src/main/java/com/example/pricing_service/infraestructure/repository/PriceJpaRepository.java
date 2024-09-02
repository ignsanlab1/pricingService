package com.example.pricing_service.infraestructure.repository;

import com.example.pricing_service.infraestructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
    Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
            Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate);
}