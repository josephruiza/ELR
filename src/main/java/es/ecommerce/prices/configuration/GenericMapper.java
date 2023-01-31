package es.ecommerce.prices.configuration;

public interface GenericMapper<K, T> {
	
	T mapperVOToDTO(K objetoVO);

}
