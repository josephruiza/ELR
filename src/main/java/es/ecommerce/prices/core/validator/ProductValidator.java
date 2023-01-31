package es.ecommerce.prices.core.validator;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import es.ecommerce.prices.core.repository.ProductRepository;

@RequiredArgsConstructor
public class ProductValidator implements ConstraintValidator<ProductValid, Integer> {
    private final ProductRepository productRepository;

    @Override
    public boolean isValid(Integer productId, ConstraintValidatorContext constraintValidatorContext) {
        return productRepository.existsPriceByProductId(productId);
    }
}
