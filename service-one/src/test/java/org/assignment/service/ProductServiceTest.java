package org.assignment.service;

import io.smallrye.mutiny.Uni;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.assignment.mapper.ProductMapper;
import org.assignment.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    private ProductService productService;
    private ProductMapper productMapperMock;

    @BeforeEach
    void setUp() {
        productMapperMock = Mockito.mock(ProductMapper.class);
        productService = new ProductService();

        Map<String, Product> productStore = new HashMap<>();
        Product product = new Product();
        product.setId("1");
        product.setName("Test Product");
        productStore.put(product.getId(), product);

        try {
            var productStoreField = ProductService.class.getDeclaredField("productStore");
            productStoreField.setAccessible(true);
            productStoreField.set(productService, productStore);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetProductWithDelay_Success() {
        Product product = new Product();
        product.setId("1");
        product.setName("Test Product");

        when(productMapperMock.toProductResponse(any(Product.class))).thenReturn(ProductResponse.builder().id("1").name("Test Product").build());

        Uni<ProductResponse> result = productService.getProductWithDelay("1");

        result.subscribe().with(productResponse -> {
            Assertions.assertNotNull(productResponse);
            Assertions.assertEquals("1", productResponse.getId());
            Assertions.assertEquals("Test Product", productResponse.getName());
        });
    }

    @Test
    void testCreateProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("New Product");

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName("New Product");

        when(productMapperMock.toProduct(any(ProductRequest.class))).thenReturn(product);
        when(productMapperMock.toProductResponse(any(Product.class))).thenReturn(ProductResponse.builder().id(product.getId()).name(product.getName()).build());

        Uni<ProductResponse> result = productService.createProduct(productRequest);

        result.subscribe().with(productResponse -> {
            Assertions.assertNotNull(productResponse);
            Assertions.assertEquals(product.getName(), productResponse.getName());
            Assertions.assertNotNull(productResponse.getId());
        });
    }
}
