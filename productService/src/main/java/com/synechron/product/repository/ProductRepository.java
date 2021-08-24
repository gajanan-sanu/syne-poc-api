package com.synechron.product.repository;

import com.synechron.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findBySkuCode(String skuCode);

    List<Product> findByCategory(String category);

    List<Product> findAllByName(String name);

    List<Product> findAllByPriceLessThanEqual(double price);

    List<Product> findAllByPriceGreaterThanEqual(double price);

    List<Product> findAllByPriceBetween(double min, double max);


}
