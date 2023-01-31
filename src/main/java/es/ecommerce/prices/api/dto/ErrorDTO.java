package es.ecommerce.prices.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorDTO {
    @Schema(description = "A date time when the error is happening")
    private Date timestamp = new Date();
    @Schema(description = "An error description")
    private String message;
}
