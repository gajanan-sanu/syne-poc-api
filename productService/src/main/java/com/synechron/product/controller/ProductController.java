package com.synechron.product.controller;

import com.synechron.product.model.APIResponse;
import com.synechron.product.model.Product;
import com.synechron.product.model.ProductResponseStatus;
import com.synechron.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(description = "Add a product")
    @PostMapping("/addProduct")
    ResponseEntity<ProductResponseStatus> addProduct(@RequestBody Product product) {
        ProductResponseStatus productResponseStatus = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseStatus);
    }

    @Operation(description = "Search a product with an ID")
    @GetMapping("/product/{id}")
    Product productById(@PathVariable Long id) {
        return productService.productById(id);
    }

    @Operation(description = "View a list of available products")
    @GetMapping("/products")
    List<Product> productList() {
        return productService.listAllProducts();
    }

    @Operation(description = "View a list of available products sorted as per provided field Ascending")
    @GetMapping("/products/sort")
    private APIResponse<List<Product>> getProductsWithSort(@RequestParam String field) {
        List<Product> allProducts = productService.listProductsWithSorting(field);
        return new APIResponse<>(allProducts.size(), allProducts);
    }

    @Operation(description = "View a list of available products sorted as per provided field Descending")
    @GetMapping("/products/sortDesc")
    private APIResponse<List<Product>> getProductsWithSortDesc(@RequestParam String field) {
        List<Product> allProducts = productService.listProductsWithSortingDesc(field);
        return new APIResponse<>(allProducts.size(), allProducts);
    }

    @Operation(description = "View a list of available products page wise")
    @GetMapping("/products/pagination")
    private APIResponse<Page<Product>> getProductsWithPagination(@RequestParam int page, @RequestParam int pageSize) {
        Page<Product> productsWithPagination = productService.findProductsWithPagination(page, pageSize);
        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }

    @Operation(description = "View a list of available products page wise and sorted as per provide field")
    @GetMapping("/products/paginationAndSort")
    private APIResponse<Page<Product>> getProductsWithPaginationAndSort(@RequestParam int page, @RequestParam int pageSize, @RequestParam String field) {
        Page<Product> productsWithPagination = productService.findProductsWithPaginationAndSorting(page, pageSize, field);
        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }

    @Operation(description = "View a list of available products based on name")
    @GetMapping("/products/{name}")
    List<Product> productListByName(@PathVariable String name) {
        return productService.productListByName(name);
    }

    @Operation(description = "View a list of available products based on category")
    @GetMapping("/productsByCategory")
    List<Product> productCategoryList(@RequestParam String category) {
        return productService.productCategoryList(category);
    }

    @Operation(description = "View a list of available products based on price range")
    @GetMapping("/productsByPriceRange")
    List<Product> productListByPriceRange(@RequestParam Double price1,@RequestParam Double price2) {
        return productService.productListByPriceRange(price1, price2);
    }

    @Operation(description = "View a list of available products based on price less than given amount")
    @GetMapping("/productsByPriceLessThan")
    List<Product> productListByPriceLessThan(@RequestParam double price) {
        return productService.productListByPriceLessThan(price);
    }

    @Operation(description = "View a list of available products based on price greater than given amount")
    @GetMapping("/productsByPriceGreaterThan")
    List<Product> productListByPriceGreaterThan(@RequestParam double price) {
        return productService.productListByPriceGreaterThan(price);
    }

    @Operation(description = "Update a product")
    @PutMapping("/updateProduct")
    ProductResponseStatus updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @Operation(description = "Delete a product")
    @DeleteMapping("/deleteProduct/{id}")
    ProductResponseStatus deleteProductById(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}
