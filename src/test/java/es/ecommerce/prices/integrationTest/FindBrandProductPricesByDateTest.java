package es.ecommerce.prices.integrationTest;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.ecommerce.prices.api.dto.ErrorDTO;
import es.ecommerce.prices.api.dto.PriceDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FindBrandProductPricesByDateTest {
    static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");

    private ResponseEntity<PriceDTO> priceResponse;
    private int expectedBrandId;
    private int expectedProductId;
    private Date expectedImplementationDate;

    private ResponseEntity<ErrorDTO> errorResponse;

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    @Before
    void setUp() {
        priceResponse = null;
        expectedBrandId = 0;
        expectedProductId = 0;
        expectedImplementationDate = null;
    }

    //region "The client asks for the price of a product {int} of a brand {int} for a {}"
    @When("The client asks for the price of a product {int} of a brand {int} for a {}")
    public void client_asks_for_the_price_of_a_product(
            int productId,
            int brandId,
            String implementationDate) throws ParseException {
        this.expectedBrandId = brandId;
        this.expectedProductId = productId;
        this.expectedImplementationDate = SIMPLE_DATE_FORMAT.parse(implementationDate);

        String url = String.format("http://localhost:%s/prices?brandId=%s&productId=%s&implementationDate=%s",
                port, brandId, productId, implementationDate);

        priceResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                PriceDTO.class);

    }

    @Then("the returned price contains the same brandId, productId, the date is in the correct window of time")
    public void assert_returned_prices_are_correct() {
        assertTrue(priceResponse.getStatusCode().is2xxSuccessful());
        PriceDTO priceDTO = Objects.requireNonNull(priceResponse.getBody());
        assertEquals(expectedBrandId, priceDTO.getBrandId());
        assertEquals(expectedProductId, priceDTO.getProductId());
        assertTrue(priceDTO.getStartDate().before(expectedImplementationDate)
                && priceDTO.getEndDate().after(expectedImplementationDate));
    }

    @Then("the expectedPriceList is {int}")
    public void the_expected_price_list_is(Integer priceList) {
        PriceDTO priceDTO = Objects.requireNonNull(priceResponse.getBody());
        assertEquals(priceList, priceDTO.getPriceList());
    }

    @Then("the expectedPrice is {float}")
    public void the_expected_price_list_is(Float expectedPrice) {
        PriceDTO priceDTO = Objects.requireNonNull(priceResponse.getBody());
        assertEquals(expectedPrice, priceDTO.getPrice());
    }
    //endregion

    //region "The client asks for the price of a non existing date"
    @When("The client asks for the price of a non existing matching date")
    public void the_client_asks_for_the_price_of_a_non_existing_matching_date() {
        String url = String.format("http://localhost:%s/prices?brandId=%s&productId=%s&implementationDate=%s",
                port, 1, 35455, "3000-11-11-11.11.11");

        errorResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                ErrorDTO.class);
    }
    @Then("the response contains a not found price message")
    public void the_response_contains_a_not_found_price_message() {
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.getStatusCode());
        assertNotNull(errorResponse.getBody());
        assertNotNull(errorResponse.getBody().getMessage());
        assertFalse(errorResponse.getBody().getMessage().isEmpty());
    }
    //endregion

    //region "The client asks for the price of a non existing brand or product"
    @When("The client asks for the price of a non existing brand")
    public void the_client_asks_for_the_price_of_a_non_existing_brand() {
        String url = String.format("http://localhost:%s/prices?brandId=%s&productId=%s&implementationDate=%s",
                port, 0, 35455, "2020-06-14-10.00.00");

        errorResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                ErrorDTO.class);

    }

    @When("The client asks for the price of a non existing product")
    public void the_client_asks_for_the_price_of_a_non_existing_product() {
        String url = String.format("http://localhost:%s/prices?brandId=%s&productId=%s&implementationDate=%s",
                port, 1, 1, "2020-06-14-10.00.00");

        errorResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                ErrorDTO.class);

    }

    @Then("the response contains an error message to inform about it")
    public void the_response_contains_an_error_message_to_inform_about_it() {
        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatusCode());
        assertNotNull(errorResponse.getBody());
        assertNotNull(errorResponse.getBody().getMessage());
        assertFalse(errorResponse.getBody().getMessage().isEmpty());
    }
    //endregion

}
