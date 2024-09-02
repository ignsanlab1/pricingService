package com.example.pricing_service.infraestructure.adapters;

import com.example.pricing_service.domain.model.Price;
import com.example.pricing_service.domain.port.PriceRepository;
import com.example.pricing_service.infraestructure.repository.PriceJpaRepository;
import com.example.pricing_service.infraestructure.rest.mapper.PriceMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper;

    public PriceRepositoryImpl(PriceJpaRepository priceJpaRepository, PriceMapper priceMapper) {
        this.priceJpaRepository = priceJpaRepository;
        this.priceMapper = priceMapper;
    }

    /**
     * Retrieves the highest-priority price for a product and brand at a given date.
     *
     * @param productId The product's ID.
     * @param brandId The brand's ID.
     * @param applicationDate The date for which the price is needed.
     * @return An {@link Optional} containing the applicable {@link Price}, or empty if no price is found.
     */
    @Override
    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceJpaRepository.findFirstByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                        productId, brandId, applicationDate, applicationDate)
                .map(priceMapper::toPrice);
    }
}
