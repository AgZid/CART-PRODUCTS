package productCart.demo.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import productCart.demo.model.Product;
import productCart.demo.validation.ProductRequestValidationException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRequestValidationService {
    public static final String NAME = "Name";
    public static final String AMOUNT = "Amount";
    public static final String PRICE = "Price";
    private final Logger LOGGER = LoggerFactory.getLogger(ProductRequestValidationService.class);

    public void validateRequest(Product product) throws ProductRequestValidationException {

        LOGGER.info("Validating request");
        List<String> missingFields = new ArrayList<>();
        List<String> incorrectFields = new ArrayList<>();

        if (StringUtils.isEmpty(product.getName())) {
            missingFields.add(NAME);
        }

        if (null == product.getAmount()) {
            missingFields.add(AMOUNT);
        } else if (product.getAmount() < 0) {
            incorrectFields.add(AMOUNT);
        }

        if (null == product.getPrice()) {
            missingFields.add(PRICE);
        } else if (product.getPrice() < 0) {
            incorrectFields.add(PRICE);
        }

        if (!missingFields.isEmpty() || !incorrectFields.isEmpty()) {
            throw new ProductRequestValidationException("Empty values " + missingFields +
                    ". Incorrect values, values must be greater than zero " + incorrectFields );
        }

        LOGGER.info("Product request validate");
    }
}
