package es.ecommerce.prices.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PriceDTO {
    @Schema(description = "A brand identifier")
    private int brandId;
    @Schema(description = "A product identifier")
    private int productId;
    @Schema(description = "A date since which a price takes effect")
    private Date startDate;
    @Schema(description = "A date up to which a price takes effect")
    private Date endDate;
    @Schema(description = "Applicable price rate identifier")
    private int priceList;
    @Schema(description = "The price of a brand product for the given range of dates")
    private float price;
}
