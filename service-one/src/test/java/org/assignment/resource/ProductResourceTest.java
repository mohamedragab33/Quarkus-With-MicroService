package org.assignment.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
 class ProductResourceTest {

    @Test
     void testGetProductWithDelay_Success() {
        given()
                .when().get("/products/1")
                .then()
                .statusCode(200)
                .body("id", is("1"),
                        "name", is("Phone"),
                        "price", is(699.99f),
                        "description", is("Latest model smartphone"));
    }

    @Test
     void testCreateProduct_Success() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("New Product");
        productRequest.setPrice(15.0);

        ProductResponse response =
                given()
                        .contentType(ContentType.JSON)
                        .body(productRequest)
                        .when().post("/products")
                        .then()
                        .statusCode(200)
                        .extract().as(ProductResponse.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("New Product", response.getName());
        assertEquals(15.0, response.getPrice());
    }

    @Test
    void testSimulateError() {
        given()
                .when().post("/products/error")
                .then()
                .statusCode(404)
                .body("errorCode", is("404"),
                        "message", is("Simulated product not found error"));
    }

}
