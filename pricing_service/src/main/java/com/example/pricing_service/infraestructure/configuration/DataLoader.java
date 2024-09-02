package com.example.pricing_service.infraestructure.configuration;

import com.example.pricing_service.infraestructure.entity.PriceEntity;
import com.example.pricing_service.infraestructure.repository.PriceJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(PriceJpaRepository priceJpaRepository) {
        return args -> {
            priceJpaRepository.save(new PriceEntity(
                    null, 1L, 35455L, 1,
                    LocalDateTime.of(2020, 6, 14, 0, 0),
                    LocalDateTime.of(2020, 12, 31, 23, 59),
                    0, 35.50, "EUR"
            ));
            priceJpaRepository.save(new PriceEntity(
                    null, 1L, 35455L, 2,
                    LocalDateTime.of(2020, 6, 14, 15, 0),
                    LocalDateTime.of(2020, 6, 14, 18, 30),
                    1, 25.45, "EUR"
            ));
            priceJpaRepository.save(new PriceEntity(
                    null, 1L, 35455L, 3,
                    LocalDateTime.of(2020, 6, 15, 0, 0),
                    LocalDateTime.of(2020, 6, 15, 11, 0),
                    1, 30.50, "EUR"
            ));
            priceJpaRepository.save(new PriceEntity(
                    null, 1L, 35455L, 4,
                    LocalDateTime.of(2020, 6, 15, 16, 0),
                    LocalDateTime.of(2020, 12, 31, 23, 59),
                    1, 38.95, "EUR"
            ));
        };
    }
}
