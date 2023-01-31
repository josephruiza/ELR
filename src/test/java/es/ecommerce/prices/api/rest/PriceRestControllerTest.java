package es.ecommerce.prices.api.rest;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import es.ecommerce.prices.api.dto.PriceDTO;
import es.ecommerce.prices.api.rest.PriceRestController;
import es.ecommerce.prices.core.exception.BrandProductPriceNotFoundException;
import es.ecommerce.prices.core.service.PriceServiceImpl;
import es.ecommerce.prices.core.vo.PriceVO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceRestControllerTest {

    @Mock
    PriceServiceImpl priceService;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    PriceRestController priceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Ignore
    @Test
    void findBrandProductPriceByDate() {
        Date now = new Date();
        PriceVO price = mock(PriceVO.class);

        when(price.getPvp()).thenReturn(1f);
        when(priceService.findBrandProductPriceByDate(1, 1, now)).thenReturn(price);
        PriceDTO priceDTO = PriceDTO.builder().build();
        priceDTO.setPrice(price.getPvp());
        when(modelMapper.map(price, PriceDTO.class)).thenReturn(priceDTO);

        PriceDTO pricesDTO = priceController.findBrandProductPriceByDate(1, 1, now);
        assertNotNull(pricesDTO);
        assertEquals(1f, pricesDTO.getPrice());
    }

    @Ignore
    @Test
    void findBrandProductPriceByDate_NotExisting() {

        PriceVO price = mock(PriceVO.class);
        int expectedBrandId = 1;
        int expectedProductId = 2;
        Date expectedTime = new Date();

        when(price.getPvp()).thenReturn(1f);
        when(priceService.findBrandProductPriceByDate(
                expectedBrandId,
                expectedProductId, expectedTime))
                .thenThrow(new BrandProductPriceNotFoundException(1, 1, expectedTime));

        var exception =
                assertThrows(BrandProductPriceNotFoundException.class, () ->
                        priceController.findBrandProductPriceByDate(expectedBrandId, expectedProductId, expectedTime));

        assertEquals(1, exception.getBrandId());
        assertEquals(1, exception.getProductId());
        assertEquals(expectedTime, exception.getDate());
    }
}
