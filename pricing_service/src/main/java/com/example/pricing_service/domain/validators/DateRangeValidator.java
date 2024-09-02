package com.example.pricing_service.domain.validators;

import com.example.pricing_service.infraestructure.entity.PriceEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;


public class DateRangeValidator implements ConstraintValidator<ValidDateRange, PriceEntity> {

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(PriceEntity priceEntity, ConstraintValidatorContext context) {
        if (priceEntity == null) {
            return true;
        }
        return priceEntity.getEndDate() != null &&
                priceEntity.getStartDate() != null &&
                priceEntity.getEndDate().isAfter(priceEntity.getStartDate());
    }
}
