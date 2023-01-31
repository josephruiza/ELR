package es.ecommerce.prices.core.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import es.ecommerce.prices.core.entity.Price;
import es.ecommerce.prices.core.exception.BrandProductPriceNotFoundException;
import es.ecommerce.prices.core.repository.PriceRepository;
import es.ecommerce.prices.core.validator.BrandValid;
import es.ecommerce.prices.core.validator.ProductValid;
import es.ecommerce.prices.core.vo.PriceVO;
import es.ecommerce.prices.log.Logged;

import java.util.Date;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final ModelMapper mapper;

    @Override
    @Logged
    public PriceVO findBrandProductPriceByDate(
            @BrandValid final int brandId,
            @ProductValid final int productId,
            @NonNull final Date implementationDate) {
        Optional<Price> brandProductPriceByDate =
                priceRepository.findBrandProductPriceByDate(brandId, productId, implementationDate);
        if (brandProductPriceByDate.isEmpty()) {
            throw new BrandProductPriceNotFoundException(brandId, productId, implementationDate);
        } else {
            return mapper.map(brandProductPriceByDate.get(), PriceVO.class);
        }
    }
}
