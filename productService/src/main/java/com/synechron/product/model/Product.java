package com.synechron.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotNull
    private String skuCode;
    @NotNull
    private String name;
    private String description;
    private double price;

    //@Enumerated(EnumType.STRING)
    private String category;

    private String image;

    private double quantity;
    private boolean isPremium;


    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}