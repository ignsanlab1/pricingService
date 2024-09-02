package com.example.pricing_service.infraestructure.rest.mapper;

import com.example.pricing_service.domain.model.Price;
import com.example.pricing_service.infraestructure.entity.PriceEntity;
import java.util.ArrayList;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-02T20:06:39+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class PriceMapperImpl implements PriceMapper {

    @Override
    public Price toPrice(PriceEntity priceEntity) {
        if ( priceEntity == null ) {
            return null;
        }

        Price price = new Price();

        price.setId( priceEntity.getId() );
        price.setBrandId( priceEntity.getBrandId() );
        price.setProductId( priceEntity.getProductId() );
        price.setPriceList( priceEntity.getPriceList() );
        price.setStartDate( priceEntity.getStartDate() );
        price.setEndDate( priceEntity.getEndDate() );
        price.setPriority( priceEntity.getPriority() );
        price.setPrice( priceEntity.getPrice() );
        price.setCurrency( priceEntity.getCurrency() );

        return price;
    }

    @Override
    public Iterable<Price> toPrices(Iterable<PriceEntity> priceEntities) {
        if ( priceEntities == null ) {
            return null;
        }

        ArrayList<Price> iterable = new ArrayList<Price>();
        for ( PriceEntity priceEntity : priceEntities ) {
            iterable.add( toPrice( priceEntity ) );
        }

        return iterable;
    }

    @Override
    public PriceEntity toPriceEntity(Price price) {
        if ( price == null ) {
            return null;
        }

        PriceEntity.PriceEntityBuilder priceEntity = PriceEntity.builder();

        priceEntity.id( price.getId() );
        priceEntity.brandId( price.getBrandId() );
        priceEntity.productId( price.getProductId() );
        priceEntity.priceList( price.getPriceList() );
        priceEntity.startDate( price.getStartDate() );
        priceEntity.endDate( price.getEndDate() );
        priceEntity.priority( price.getPriority() );
        priceEntity.price( price.getPrice() );
        priceEntity.currency( price.getCurrency() );

        return priceEntity.build();
    }
}
