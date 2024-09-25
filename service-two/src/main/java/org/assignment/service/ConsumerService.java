package org.assignment.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.assignment.exception.ProductNotFoundException;
import org.assignment.rest.ProductServiceClient;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

@ApplicationScoped
public class ConsumerService {

    private static final Logger logger = Logger.getLogger(ConsumerService.class.getName());

    @Inject
    @RestClient
    ProductServiceClient productServiceClient;

    public Uni<ProductResponse> getProduct(String id) {
        logger.info("ConsumerService.getProduct() called");
        return productServiceClient.getProductWithDelay(id)
                .onItem().invoke(productResponse -> {
                    logger.info("Product response received: " + productResponse);
                })
                .onFailure().invoke(throwable -> {
                    logger.severe("Failed to retrieve product: " + throwable.getMessage());
                });
    }

    @CircuitBreaker
    public Uni<ProductResponse> createProduct(ProductRequest productRequest) {
        logger.info("ConsumerService.createProduct() called");
        return productServiceClient.createProduct(productRequest)
                .onFailure().invoke(throwable -> logger.severe("Failed to create product: " + throwable.getMessage()));
    }


    @CircuitBreaker
    @Retry(delay = 1000, maxRetries = 5)
    public Uni<Void> throwException() {
        logger.info("ConsumerService.throwException() called");
        return productServiceClient.throwException()
                .onFailure().invoke(throwable -> {
                    if (throwable instanceof ProductNotFoundException) {
                        logger.severe("Product not found: " + throwable.getMessage());
                        throw new ProductNotFoundException("Error simulating successful");
                    } else {
                        logger.severe("Failed to throw exception: " + throwable.getMessage());
                        throw new RuntimeException("Failed to throw exception");
                    }
                });
    }


}
