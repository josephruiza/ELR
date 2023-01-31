package es.ecommerce.prices.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.ecommerce.prices.api.dto.ErrorDTO;
import es.ecommerce.prices.api.dto.PriceDTO;
import es.ecommerce.prices.configuration.MapperTest;
import es.ecommerce.prices.core.service.PriceService;
import es.ecommerce.prices.core.validator.ProductValid;
import es.ecommerce.prices.core.vo.PriceVO;
import es.ecommerce.prices.log.Logged;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prices")
public class PriceRestController {
    private final PriceService priceService;
    private final ModelMapper mapper;

    /**
     * <p>Find a price for the given brand and product where the ranges between start and end dates contains the given implementation date.</p>
     * <p>If more than a price match for the given date, then return the one with the higher priority.</p>
     *
     * @param brandId            a brand identifier
     * @param productId          a product identifier
     * @param implementationDate the date of implementation of the price. The string format is {@code yyyy-MM-dd-HH.mm.ss}
     * @return a {@link PriceDTO} with the price with higher start date and lower end date than the given implementation date.
     */
    @Operation(summary = " Find a price for the given brand and product where the ranges between start and end dates contains the given implementation date.\n" +
            "If more than a price match for the given date, then return the one with the higher priority.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Price found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PriceDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "The brand or product do not exists or they are invalid.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "BrandId, productId not found, or there is no price matching for the given date.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))})
    })
    @Logged
    @GetMapping
    public PriceDTO findBrandProductPriceByDate(
            @RequestParam
            @Parameter(
                    description = "A brand identifier",
                    example = "1") final int brandId,
            @ProductValid
            @RequestParam
            @Parameter(
                    description = "A product identifier",
                    example = "35455") final int productId,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
            @Parameter(
                    description = "The date of implementation of the price. The string format is `yyyy-MM-dd-HH.mm.ss`",
                    schema = @Schema(
                            type = "string",
                            pattern = "^\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.\\d{2}\\.\\d{2}$",
                            example = "2020-06-14-10.00.00")) final Date implementationDate) {
    	
    	MapperTest mapperTest= new MapperTest();
    			
    	return mapperTest.mapperVOToDTO(priceService.findBrandProductPriceByDate(brandId, productId, implementationDate));
    	/*
        return mapper.map(
                priceService.findBrandProductPriceByDate(brandId, productId, implementationDate),
                PriceDTO.class);
                */
    }
}
