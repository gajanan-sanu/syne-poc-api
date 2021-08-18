package com.synechron.product.controller;

import com.synechron.product.model.Product;
import com.synechron.product.model.ProductResponse;
import com.synechron.product.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Operation(description = "Add a product")
    @PostMapping("/addProduct")
    ResponseEntity<ProductResponse> addProduct(@RequestBody Product product) {
        ProductResponse productResponse = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @Operation(description = "View a list of available products")
    @GetMapping("/productList")
    List<Product> productList() {
        return productService.listAllProducts();
    }

    @Operation(description = "View a list of available products based on category")
    @GetMapping("/productList/{category}")
    List<Product> productCategoryList(@PathVariable String category) {
        return productService.productCategoryList(category);
    }

    @Operation(description = "Search a product with an ID")
    @GetMapping("/product/{id}")
    Product productById(@PathVariable Long id) {
        return productService.productById(id);
    }

    @Operation(description = "Search a product with name")
    @GetMapping("/product/{name}")
    Product productByName(@PathVariable String name) {
        return productService.productByName(name);
    }

    @Operation(description = "Update a product")
    @PutMapping("/productUpdate")
    ProductResponse updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @Operation(description = "Delete a product")
    @DeleteMapping("/product/{id}")
    ProductResponse deleteProductById(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}
