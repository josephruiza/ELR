package es.ecommerce.prices.core.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PriceVO {
    private int id;

    private int brandId;

    private Date startDate;

    private Date endDate;

    private int priceList;

    private int productId;

    private int priority;

    private float pvp;

    private String currency;
}
