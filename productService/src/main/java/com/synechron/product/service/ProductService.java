package com.synechron.product.service;

import com.synechron.product.exception.ProductNotFoundException;
import com.synechron.product.model.Product;
import com.synechron.product.model.ProductResponse;
import com.synechron.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse addProduct(Product product) {
        log.info("Inside addProduct() method");

        Product savedProduct = productRepository.save(product);

        return new ProductResponse("success",savedProduct.getName() + " added into the DB system");
    }

    public List<Product> listAllProducts() {
        log.info("Inside listAllProducts() method");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No product found for the given query");
        }
        return products;
    }

    public List<Product> productCategoryList(String category) {
        log.info("Inside productCategoryList() method");
        List<Product> productsByCategory = productRepository.findByCategory(category);
        if (productsByCategory.isEmpty()) {
            throw new ProductNotFoundException("No product found for the category-" + category);
        }
        return productsByCategory;
    }

    public Product productById(Long id) {
        log.info("Inside productById method");
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for id - " + id));
    }

    public ProductResponse updateProduct(Product product) {
        log.info("Inside updateProduct method");
        Optional<Product> prod = productRepository.findById(product.getId());
        if (!prod.isPresent()) {
            return new ProductResponse("FAILED", "Product to be updated not found in the system");
        }

        Product updatedProduct = productRepository.save(product);

        return new ProductResponse("SUCCESS", "Product Updated - " + updatedProduct.getName());
    }

   public ProductResponse deleteProductById(Long id) {
        log.info("Inside deleteProductById method");
        Optional<Product> prod = productRepository.findById(id);
        if (!prod.isPresent()) {
            return new ProductResponse("FAILED", "Product to be deleted not found in the system");
        }

        productRepository.deleteById(id);

        return new ProductResponse("SUCCESS", "Product Deleted");
    }

    public Product productByName(String name) {
        log.info("Inside productByName method");
       return productRepository.findByName(name);
//               .orElseThrow(() -> new ProductNotFoundException("Product not found for name - " + name));
    }
}
