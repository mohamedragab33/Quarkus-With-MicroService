package org.assignment.mapper;

import org.assignment.dto.ProductRequest;
import org.assignment.dto.ProductResponse;
import org.assignment.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequest productRequest);
    ProductResponse toProductResponse(Product product);
}
