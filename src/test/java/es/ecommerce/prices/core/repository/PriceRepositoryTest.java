package es.ecommerce.prices.core.repository;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.ecommerce.prices.core.entity.Price;
import es.ecommerce.prices.core.repository.PriceRepository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PriceRepositoryTest {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");

    @Autowired
    PriceRepository priceRepository;

    @TestFactory
    Stream<DynamicTest> findBrandProductPriceByDate() {
        Map<String, Price> expectedCases = Map.ofEntries(
                new AbstractMap.SimpleEntry<>("2020-06-14-10.00.00", Price.builder().priority(0).priceList(1).build()),
                new AbstractMap.SimpleEntry<>("2020-06-14-16.00.00", Price.builder().priority(1).priceList(2).build()),
                new AbstractMap.SimpleEntry<>("2020-06-14-21.00.00", Price.builder().priority(0).priceList(1).build()),
                new AbstractMap.SimpleEntry<>("2020-06-15-10.00.00", Price.builder().priority(1).priceList(3).build()),
                new AbstractMap.SimpleEntry<>("2020-06-16-21.00.00", Price.builder().priority(1).priceList(4).build())
        );

        return expectedCases.entrySet().stream().map(expectedCase ->
                DynamicTest.dynamicTest(String.format("Testing price %s", expectedCase.getKey()), () -> {
                    Date queryDate = SIMPLE_DATE_FORMAT.parse(expectedCase.getKey());
                    Optional<Price> optionalPrice = priceRepository.findBrandProductPriceByDate(
                            1,
                            35455,
                            queryDate);
                    assertTrue(optionalPrice.isPresent());
                    Price price = optionalPrice.get();

                    assertEquals(expectedCase.getValue().getPriority(), price.getPriority());
                    assertEquals(expectedCase.getValue().getPriceList(), price.getPriceList());
                    assertTrue(price.getStartDate().before(queryDate));
                    assertTrue(price.getEndDate().after(queryDate));
                }));
    }

    @Test
    void findAll() {
        List<Price> all = priceRepository.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void save() {
        Price price = priceRepository.save(Price.builder()
                .brandId(1)
                .startDate(new Date())
                .endDate(new Date())
                .priceList(1)
                .productId(1)
                .priority(1)
                .pvp(0f)
                .currency("EUR").build());
        assertTrue(price.getId() >= 1);
    }
}