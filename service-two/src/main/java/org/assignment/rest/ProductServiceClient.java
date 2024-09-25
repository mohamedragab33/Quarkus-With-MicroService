package org.assignment.rest;

import io.smallrye.mutiny.Uni;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "service-one-api")
@Path("/products")
public interface ProductServiceClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<ProductResponse> getProductWithDelay(@PathParam("id") String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<ProductResponse> createProduct(ProductRequest productRequest);

    @POST
    @Path("/throw")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Void> throwException();
}
