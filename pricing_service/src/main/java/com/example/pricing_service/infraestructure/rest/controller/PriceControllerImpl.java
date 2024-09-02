package com.example.pricing_service.infraestructure.rest.controller;

import com.example.pricing_service.domain.port.PriceController;
import com.example.pricing_service.domain.dto.request.PriceRequest;
import com.example.pricing_service.application.service.PriceService;
import com.example.pricing_service.infraestructure.commons.constants.ApiPathVariables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.example.pricing_service.infraestructure.commons.constants.ExceptionMessages.*;

@RestController
@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.PRICES_ROUTE)
public class PriceControllerImpl implements PriceController {

    private final PriceService priceService;

    public PriceControllerImpl(PriceService priceService) {
        this.priceService = priceService;
    }

    /**
     * Retrieves the applicable price for a given product and brand at a specific date.
     *
     * @param productId      The ID of the product.
     * @param brandId        The ID of the brand.
     * @param applicationDate The date and time when the price should be applicable.
     * @return A {@link ResponseEntity} containing the {@link PriceRequest} with the applicable price details.
     */
    @GetMapping
    @Operation(
            summary = "Retrieve applicable price",
            description = "Fetches the applicable price for a specified product and brand at a given date and time."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OK_RESPONSE),
            @ApiResponse(responseCode = "400", description = PRICE_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = PRICE_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<PriceRequest> getPrice(
            @RequestParam Long productId,
            @RequestParam Long brandId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        PriceRequest priceRequest = priceService.getApplicablePrice(productId, brandId, applicationDate);
        return ResponseEntity.ok(priceRequest);
    }
}
