package es.ecommerce.prices.core.service;

import lombok.NonNull;

import java.util.Date;

import es.ecommerce.prices.core.validator.BrandValid;
import es.ecommerce.prices.core.validator.ProductValid;
import es.ecommerce.prices.core.vo.PriceVO;

public interface PriceService {
    /**
     * <p>Find a price for the given brand and product where the ranges between start and end dates contains the given implementation date.</p>
     * <p>If more than a price match for the given date, then return the one with the higher priority.</p>
     *
     * @param brandId            a brand identifier
     * @param productId          a product identifier
     * @param implementationDate the date of implementation of the price
     * @return a {@link PriceVO} with the price with higher start date and lower end date than the given implementation date.
     */
    PriceVO findBrandProductPriceByDate(
            @BrandValid final int brandId,
            @ProductValid final int productId,
            @NonNull final Date implementationDate);
}
