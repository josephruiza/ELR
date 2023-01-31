package es.ecommerce.prices.configuration;

import es.ecommerce.prices.api.dto.PriceDTO;
import es.ecommerce.prices.core.vo.PriceVO;

public class MapperTest implements GenericMapper<PriceVO, PriceDTO>  {
	

	
	public MapperTest() {
	}
	
	@Override
	public PriceDTO mapperVOToDTO(PriceVO priceVO ) {
		PriceDTO priceDTOMapped = PriceDTO.builder().brandId(priceVO.getBrandId()).endDate(priceVO.getEndDate())
				.price(priceVO.getPvp()).priceList(priceVO.getPriceList()).productId(priceVO.getProductId())
				.productId(priceVO.getProductId()).startDate(priceVO.getStartDate()).build();
		return priceDTOMapped;
	}



}
