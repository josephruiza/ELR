package es.ecommerce.prices.api.rest;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//TODO @Hidden y por que un defaultController


@Hidden
@RestController
public class DefaultController {
    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerURL;

    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(swaggerURL);
    }
}

