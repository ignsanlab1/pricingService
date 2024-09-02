package com.example.pricing_service.application.service.impl;

import com.example.pricing_service.application.validation.ValidationResult;
import com.example.pricing_service.domain.dto.request.PriceRequest;
import com.example.pricing_service.application.service.PriceService;
import com.example.pricing_service.domain.model.Price;
import com.example.pricing_service.infraestructure.commons.exceptions.PriceBadRequestException;
import com.example.pricing_service.infraestructure.commons.exceptions.PriceNotFoundException;
import com.example.pricing_service.domain.port.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.pricing_service.infraestructure.commons.constants.ExceptionMessages.PRICE_BAD_REQUEST;
import static com.example.pricing_service.infraestructure.commons.constants.ExceptionMessages.PRICE_NOT_FOUND;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Fetches the applicable price for a given product and brand on a specific date.
     *
     * @param productId The ID of the product.
     * @param brandId The ID of the brand.
     * @param applicationDate The date to find the applicable price for.
     * @return A {@link PriceRequest} with the price details.
     * @throws PriceNotFoundException if no price is found.
     */
    @Override
    public PriceRequest getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return validateParameters(productId, brandId, applicationDate)
                .filter(ValidationResult::valid)
                .map(result -> fetchPrice(productId, brandId, applicationDate)
                        .orElseThrow(() -> new PriceNotFoundException(PRICE_NOT_FOUND)))
                .orElseThrow(() -> new PriceBadRequestException(PRICE_BAD_REQUEST));
    }

    /**
     * Validates the input parameters to ensure they are not null and have valid values.
     *
     * @param productId The ID of the product.
     * @param brandId The ID of the brand.
     * @param applicationDate The date to find the applicable price for.
     * @return An {@link Optional} containing a {@link ValidationResult} indicating if the parameters are valid or not.
     */
    private Optional<ValidationResult> validateParameters(Long productId, Long brandId, LocalDateTime applicationDate) {
        if (productId == null || productId <= 0L || brandId == null || brandId <= 0L || applicationDate == null) {
            return Optional.of(new ValidationResult(false, "Invalid input parameters"));
        }
        return Optional.of(new ValidationResult(true, ""));
    }

    /**
     * Retrieves the applicable price from the repository based on the given parameters.
     *
     * @param productId The ID of the product.
     * @param brandId The ID of the brand.
     * @param applicationDate The date to find the applicable price for.
     * @return An {@link Optional} containing a {@link PriceRequest} if a price is found, or empty if not.
     */
    private Optional<PriceRequest> fetchPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceRepository.findApplicablePrice(productId, brandId, applicationDate)
                .map(this::mapToDomainPriceRequest);
    }

    /**
     * Converts the {@link Price} object into a {@link PriceRequest} to be used by the service layer.
     *
     * @param price The model to convert.
     * @return A {@link PriceRequest} with the mapped price details.
     */
    private PriceRequest mapToDomainPriceRequest(Price price) {
        return PriceRequest.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }

}
