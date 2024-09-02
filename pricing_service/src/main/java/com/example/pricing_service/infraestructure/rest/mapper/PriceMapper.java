package com.example.pricing_service.infraestructure.rest.mapper;

import com.example.pricing_service.domain.model.Price;
import com.example.pricing_service.infraestructure.entity.PriceEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "brandId", target = "brandId"),
            @Mapping(source = "productId", target = "productId"),
            @Mapping(source = "priceList", target = "priceList"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "priority", target = "priority"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "currency", target = "currency")
    })
    Price toPrice(PriceEntity priceEntity);

    Iterable<Price> toPrices(Iterable<PriceEntity> priceEntities);

    @InheritInverseConfiguration
    PriceEntity toPriceEntity(Price price);
}

