package es.ecommerce.prices.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.ecommerce.prices.api.dto.PriceDTO;
import es.ecommerce.prices.core.vo.PriceVO;

@Configuration
public class BeanConfiguration {
    @Bean
    ModelMapper createModelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.typeMap(PriceVO.class, PriceDTO.class).addMappings(mapper ->
                mapper.map(PriceVO::getPvp, PriceDTO::setPrice)
        );
        return modelMapper;
    }
}
