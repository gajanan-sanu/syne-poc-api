package com.synechron.user.controller;

import com.synechron.user.model.AppUser;
import com.synechron.user.model.AppUserResponse;
import com.synechron.user.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AppUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    private AppUserService appuserServie;

    @Operation(description = "Adding a Application user")
    @PostMapping("/register")
    ResponseEntity<AppUserResponse> addProduct(@RequestBody AppUser appUser) {
        AppUserResponse appUserResponse = appuserServie.userSingUp(appUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserResponse);
    }


    @Operation(description = "get user details for sign in")
    @GetMapping("/adminPage")
    String getadminPage() {
        return "this is admin page";
    }


    @Operation(description = "get user details for sign in")
    @PostMapping("/login/{emailId}")
    AppUser getUseDetails(@PathVariable AppUser appUser) {
        return appuserServie.getUseDetails(appUser.getEmailId());
    }

    @Operation(description = "get user details for sign in")
    @PostMapping("/resetPwd")
    AppUserResponse resetPwd(@RequestBody AppUser appUser) {
        return appuserServie.resetUserPwd(appUser);
    }



/*
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
*/


}
