package org.assignment.resource;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.assignment.exception.ProductCreationException;
import org.assignment.exception.ProductNotFoundException;
import org.assignment.service.ConsumerService;

import java.util.logging.Logger;

@Path("/consumer/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Blocking
public class ConsumerResource {

    private static final Logger logger = Logger.getLogger(ConsumerResource.class.getName());

    @Inject
    ConsumerService consumerService;

    @GET
    @Path("/{id}")
    public Uni<ProductResponse> getProductWithDelay(@PathParam("id") String id) {
        return consumerService.getProduct(id)
                .onItem().invoke(product -> logger.info("Product received: " + product))
                .onFailure().transform(throwable -> {
                    logger.severe("Failed to retrieve product: " + throwable.getMessage());
                    return new ProductNotFoundException("Product not found with id: " + id);
                });
    }

    @POST
    public Uni<ProductResponse> createProduct(ProductRequest productRequest) {
        return consumerService.createProduct(productRequest)
                .onItem().invoke(product -> logger.info("Product created: " + product))
                .onFailure().transform(throwable -> {
                    logger.severe("Failed to create product: " + throwable.getMessage());
                    return new ProductCreationException("Failed to create product");
                });
    }

    @POST
    @Path("/throw")
    public Uni<Void> throwException() {
        return consumerService.throwException()
                .onFailure().transform(throwable -> {
                    logger.severe("Exception thrown in ConsumerService: " + throwable.getMessage());
                    return new RuntimeException("Handled error in ConsumerResource");
                });
    }
}
