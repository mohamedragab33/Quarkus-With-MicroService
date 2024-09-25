package org.assignment.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Error {
        public static final String FAILED_TO_LOAD_PREDEFINED_PRODUCTS_FROM_JSON = "Failed to load predefined products from JSON";
        public static final String PRODUCTS_JSON_NOT_FOUND_IN_RESOURCES = "products.json not found in resources";
        public static final String SIMULATED_PRODUCT_NOT_FOUND_ERROR = "Simulated product not found error";




    }
}
