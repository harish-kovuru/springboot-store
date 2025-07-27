package com.springbootcode.store.controllers;

import com.springbootcode.store.dto.ProductDto;
import com.springbootcode.store.entities.Product;
import com.springbootcode.store.mappers.ProductMapper;
import com.springbootcode.store.repositories.CategoryRepository;
import com.springbootcode.store.repositories.ProductRepository;
import com.springbootcode.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestParam(required = false, name = "categoryId") Byte categoryId
            ) {
        if(!Set.of("id", "name", "description", "price").contains(sort))
            sort="name";
        List<Product> products;
        if(categoryId != null){
            products = productRepository.findByCategoryId(categoryId);
        }
        else{
            products = productRepository.findAllwithCategory();
        }
        return  products.stream().map(productMapper::toProduct).toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var product= productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toProduct(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder) {

        var catergory = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(catergory == null) {
            return ResponseEntity.notFound().build();
        }
        var product = productMapper.toProduct(productDto);
        product.setCategory(catergory);
        productRepository.save(product);
        productDto.setId(product.getId());
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ){
        var catergory = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(catergory == null) {
            return ResponseEntity.notFound().build();
        }
        var product= productRepository.findById(id).orElse(null);
        if(product == null) { 
            return ResponseEntity.notFound().build();
        }
        productMapper.updateProduct(productDto, product);
        productRepository.save(product);
        productDto.setId(product.getId());

        return ResponseEntity.ok(productDto);
    }


    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        var product= productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }



}
