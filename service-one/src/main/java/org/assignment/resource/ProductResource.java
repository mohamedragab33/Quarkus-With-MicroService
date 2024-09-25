package org.assignment.resource;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.assignment.exception.ProductNotFoundException;
import org.assignment.service.ProductService;

import static org.assignment.constant.ErrorMessages.Error.SIMULATED_PRODUCT_NOT_FOUND_ERROR;

@Path("/products")
@ApplicationScoped
public class ProductResource {

    private final ProductService productService;

    @Inject
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ProductResponse> getProductWithDelay(@PathParam("id") String id) {
        return productService.getProductWithDelay(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ProductResponse> createProduct(ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @POST
    @Path("/throw")
    public Uni<Void> throwException() {
        return Uni.createFrom().failure(new ProductNotFoundException(SIMULATED_PRODUCT_NOT_FOUND_ERROR));
    }

}
