package org.assignment.service;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.assignment.exception.ProductNotFoundException;
import org.assignment.mapper.ProductMapper;
import org.assignment.model.Product;
import org.assignment.utils.ProductUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
@ApplicationScoped
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class.getName());
    private Map<String, Product> productStore;

    @PostConstruct
    public void init() {
        productStore = new HashMap<>(ProductUtils.loadProductsFromJson());
    }

    public Uni<ProductResponse> getProductWithDelay(String id) {
        Product product = productStore.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        return Uni.createFrom().item(ProductMapper.INSTANCE.toProductResponse(product))
                .onItem().delayIt().by(Duration.ofSeconds(3));
    }

    public Uni<ProductResponse> createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.INSTANCE.toProduct(productRequest);
        product.setId(UUID.randomUUID().toString());
        productStore.put(product.getId(), product);

        logger.info(() -> "Product created: " + product);

        return Uni.createFrom().item(ProductMapper.INSTANCE.toProductResponse(product));
    }
}
