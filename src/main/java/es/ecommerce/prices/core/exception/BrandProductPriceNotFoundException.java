package es.ecommerce.prices.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class BrandProductPriceNotFoundException extends RuntimeException {
    private final int brandId;
    private final int productId;
    private final Date date;

    @Override
    public String getMessage() {
        return String.format("Price not found for the brandId %s and the productId %s by the date %s",
                brandId,
                productId,
                date);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
