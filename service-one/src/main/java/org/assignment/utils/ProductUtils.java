package org.assignment.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assignment.exception.ProductFileNotFoundException;
import org.assignment.exception.ProductFileReadException;
import org.assignment.model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static org.assignment.constant.ErrorMessages.Error.*;

public class ProductUtils {

    private static final Logger logger = Logger.getLogger(ProductUtils.class.getName());

    private ProductUtils() {}

    public static Map<String, Product> loadProductsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Product> productStore = new HashMap<>();

        try (InputStream is = ProductUtils.class.getClassLoader().getResourceAsStream("products.json")) {
            if (is != null) {
                List<Product> products = objectMapper.readValue(is, new TypeReference<>() {});
                products.forEach(product -> productStore.put(product.getId(), product));
                logger.info(() -> "Predefined products loaded: " + productStore);
                return productStore;
            } else {
                throw new ProductFileNotFoundException(PRODUCTS_JSON_NOT_FOUND_IN_RESOURCES);
            }
        } catch (IOException e) {
            throw new ProductFileReadException(FAILED_TO_LOAD_PREDEFINED_PRODUCTS_FROM_JSON, e);
        }
    }
}
