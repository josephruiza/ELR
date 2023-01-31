package es.ecommerce.prices.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ecommerce.prices.core.entity.Price;
import es.ecommerce.prices.log.Logged;

import java.util.Date;
import java.util.Optional;

//TODO por que esta interface?


public interface PriceRepository extends JpaRepository<Price, Integer> {
    /**
     * <p>Find a price for the given brand and product where the ranges between start and end dates contains the given implementation date.</p>
     * <p>If more than a price match for the given date, then return the one with the higher priority.</p>
     *
     * Please, check a simpler version of this method at {@link #findBrandProductPriceByDate(int, int, Date)}
     *
     * @param brandId            a brand identifier
     * @param productId          a product identifier
     * @param implementationDate the date of implementation of the price
     * @return a {@link Price} with the price with higher start date and lower end date than the given implementation date.
     */
    Optional<Price> findTop1ByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(int brandId, int productId, Date implementationDate, Date implementationDate2);

    /**
     * <p>Find a price for the given brand and product where the ranges between start and end dates contains the given implementation date.</p>
     * <p>If more than a price match for the given date, then return the one with the higher priority.</p>
     *
     * @param brandId            a brand identifier
     * @param productId          a product identifier
     * @param implementationDate the date of implementation of the price
     * @return a {@link Price} with the price with higher start date and lower end date than the given implementation date.
     */
    @Logged
    default Optional<Price> findBrandProductPriceByDate(int brandId, int productId, Date implementationDate) {
        return findTop1ByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(brandId, productId, implementationDate, implementationDate);
    }
}
