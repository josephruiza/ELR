package es.ecommerce.prices.core.entity;

import lombok.*;

import java.util.Date;
import javax.persistence.*;

@Entity(name = "prices")
@Table(name = "PRICES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "BRAND_ID", nullable = false)
    private int brandId;

    @Column(name="START_DATE", nullable = false)
    private Date startDate;

    @Column(name="END_DATE", nullable = false)
    private Date endDate;

    @Column(name="PRICE_LIST", nullable = false)
    private int priceList;

    @Column(name="PRODUCT_ID", nullable = false)
    private int productId;

    @Column(name="PRIORITY", nullable = false)
    private int priority;

    @Column(name="PRICE", nullable = false)
    private float pvp;

    @Column(name="CURRENCY", nullable = false)
    private String currency;
}


