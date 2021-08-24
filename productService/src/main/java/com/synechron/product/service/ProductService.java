package com.synechron.product.service;

import com.synechron.product.exception.ProductNotFoundException;
import com.synechron.product.model.Product;
import com.synechron.product.model.ProductResponseStatus;
import com.synechron.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponseStatus addProduct(Product product) {
        log.info("Invoked addProduct() method");
        List<Product> products = productRepository.findAll();
        products.forEach(p->{
            if (p.getSkuCode().equals(product.getSkuCode())){
                throw new ProductNotFoundException("Product is already available in the system");
            }
        });
        product.setCreatedDate(LocalDateTime.now());
        product.setModifiedDate(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        return new ProductResponseStatus("success",savedProduct.getName() + " added into the system");
    }

    public List<Product> listAllProducts() {
        log.info("Invoked listAllProducts() method");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No product found for the given query");
        }
        return products;
    }

    public List<Product> productCategoryList(String category) {
        log.info("Invoked productCategoryList() method");
        List<Product> productsByCategory = productRepository.findByCategory(category);
        if (productsByCategory.isEmpty()) {
            throw new ProductNotFoundException("No product found for the category-" + category);
        }
        return productsByCategory;
    }

    public Product productById(Long id) {
        log.info("Invoked productById method");
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for id - " + id));
    }

    public ProductResponseStatus updateProduct(Product product) {
        log.info("Invoked updateProduct method");

        Optional<Product> prod = productRepository.findById(product.getId());
        if (!prod.isPresent()) {
            return new ProductResponseStatus("FAILED", "Product to be updated not found in the system");
        }
        product.setCreatedDate(prod.get().getCreatedDate());
        product.setModifiedDate(LocalDateTime.now());
        Product updatedProduct = productRepository.save(product);

        return new ProductResponseStatus("SUCCESS", "Product Updated - " + updatedProduct.getName());
    }

   public ProductResponseStatus deleteProductById(Long id) {
        log.info("Invoked deleteProductById method");
        Optional<Product> prod = productRepository.findById(id);
        if (!prod.isPresent()) {
            return new ProductResponseStatus("FAILED", "Product to be deleted not found in the system");
        }
        productRepository.deleteById(id);
        return new ProductResponseStatus("SUCCESS", "Product Deleted");
    }

    public List<Product> productListByName(String name) {
        log.info("Invoked productListByName() method");
        List<Product> productListByName = productRepository.findAllByName(name);
        if (productListByName.isEmpty()) {
            throw new ProductNotFoundException("No product found for the name-" + name);
        }
        return productListByName;
    }
    public List<Product> productListByPriceGreaterThan(double price) {
        log.info("Invoked productListByPrice() method");
        List<Product> productListByPrice = productRepository.findAllByPriceGreaterThanEqual(price);
        if (productListByPrice.isEmpty()) {
            throw new ProductNotFoundException("No product found in the price less than " + price);
        }
        return productListByPrice;
    }
    public List<Product> productListByPriceLessThan(double price) {
        log.info("Invoked productListByPrice() method");
        List<Product> productListByPrice = productRepository.findAllByPriceLessThanEqual(price);
        if (productListByPrice.isEmpty()) {
            throw new ProductNotFoundException("No product found in the price less than " + price);
        }
        return productListByPrice;
    }
    public List<Product> productListByPriceRange(double price1, double price2) {
        log.info("Invoked productListByPriceRange() method");
        List<Product> productListByPriceRange = productRepository.findAllByPriceBetween(price1,price2);
        if (productListByPriceRange.isEmpty()) {
            throw new ProductNotFoundException("No product found in the price range between " + price1+"-"+price2);
        }
        return productListByPriceRange;
    }

    public List<Product> listProductsWithSorting(String field){

        log.info("Invoked listProductsWithSorting() method");
        List<Product> listAllProductsSorted = productRepository.findAll(Sort.by(Sort.Direction.ASC,field));
        if (listAllProductsSorted.isEmpty()) {
            throw new ProductNotFoundException("No product found");
        }
        return listAllProductsSorted;
    }
    public List<Product> listProductsWithSortingDesc(String field){

        log.info("Invoked listProductsWithSortingDesc() method");
        List<Product> listAllProductsSorted = productRepository.findAll(Sort.by(Sort.Direction.DESC,field));
        if (listAllProductsSorted.isEmpty()) {
            throw new ProductNotFoundException("No product found");
        }
        return listAllProductsSorted;
    }
    public Page<Product> findProductsWithPagination(int offset,int pageSize){
        Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize));
        return  products;
    }

    public Page<Product> findProductsWithPaginationAndSorting(int offset,int pageSize,String field){
        Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return  products;
    }
//    public List<Product> listAllProductsByPagewise(Pageable pageable) {
//        log.info("Invoked listAllProductsByPage() method");
//        List<Product> listAllProductsByPage = productRepository.findAll(pageable).toList();
//        if (listAllProductsByPage.isEmpty()) {
//            throw new ProductNotFoundException("No product found");
//        }
//        return listAllProductsByPage;
//    }


}
