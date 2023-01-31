package es.ecommerce.prices.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import es.ecommerce.prices.core.entity.Price;
import es.ecommerce.prices.core.exception.BrandProductPriceNotFoundException;
import es.ecommerce.prices.core.repository.PriceRepository;
import es.ecommerce.prices.core.service.PriceServiceImpl;
import es.ecommerce.prices.core.vo.PriceVO;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceServiceImplTest {

    @Mock
    PriceRepository priceRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    PriceServiceImpl priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(modelMapper.map(any(Price.class), eq(PriceVO.class))).thenReturn(mock(PriceVO.class));
    }

    @Test
    void findBrandProductPriceByDate() {
        Date now = new Date();
        when(priceRepository.findBrandProductPriceByDate(1, 1, now)).thenReturn(Optional.of(mock(Price.class)));
        PriceVO price = priceService.findBrandProductPriceByDate(1, 1, now);
        assertNotNull(price);
    }

    @Test
    void findBrandProductPriceByDate_NotFound() {
        Date now = new Date();
        when(priceRepository.findBrandProductPriceByDate(1, 1, now)).thenReturn(Optional.empty());
        assertThrows(BrandProductPriceNotFoundException.class, () ->
                priceService.findBrandProductPriceByDate(1, 1, now));
    }
}