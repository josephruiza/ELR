package es.ecommerce.prices.core.repository;

import org.springframework.data.repository.Repository;

import es.ecommerce.prices.core.entity.Price;

public interface BrandRepository extends Repository<Price, Integer> {
    boolean existsPriceByBrandId(int brandId);
}
