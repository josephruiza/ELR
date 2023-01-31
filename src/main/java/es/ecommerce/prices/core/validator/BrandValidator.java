package es.ecommerce.prices.core.validator;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import es.ecommerce.prices.core.repository.BrandRepository;

@RequiredArgsConstructor
public class BrandValidator implements ConstraintValidator<BrandValid, Integer> {
    private final BrandRepository brandRepository;

    @Override
    public boolean isValid(Integer brandId, ConstraintValidatorContext constraintValidatorContext) {
        return brandRepository.existsPriceByBrandId(brandId);
    }
}
