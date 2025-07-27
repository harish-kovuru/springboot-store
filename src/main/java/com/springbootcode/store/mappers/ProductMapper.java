package com.springbootcode.store.mappers;

import com.springbootcode.store.dto.ProductDto;
import com.springbootcode.store.entities.Category;
import com.springbootcode.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toProduct(Product product);

    Product toProduct(ProductDto productDto);
    @Mapping(target = "id", ignore = true)
    void updateProduct( ProductDto productDto, @MappingTarget Product product);
}
