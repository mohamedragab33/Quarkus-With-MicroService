package org.assignment.resource;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.assignment.exception.ProductNotFoundException;
import org.assignment.service.ConsumerService;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

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
        return consumerService.getProduct(id);
    }

    @POST
    public Uni<ProductResponse> createProduct(ProductRequest productRequest) {
        return consumerService.createProduct(productRequest);
    }

    @POST
    @Path("/throw")
    public Uni<Void> throwException() {
        return consumerService.throwException();
    }
}
