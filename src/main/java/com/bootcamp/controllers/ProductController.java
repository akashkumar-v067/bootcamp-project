package com.bootcamp.controllers;

import com.bootcamp.dto.product.ProductDto;
import com.bootcamp.dto.product.ViewProductDto;
import com.bootcamp.entities.product.Product;
import com.bootcamp.exceptions.NotFoundException;
import com.bootcamp.service.CurrentUserService;
import com.bootcamp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/product")
public class ProductController {
   @Autowired
    ProductService productService;
   @Autowired
    CurrentUserService currentUserService;

    @PostMapping("/seller/addProduct/{category}")
    String addProduct(@RequestBody Product product, @PathVariable(name = "category") Long category) throws NotFoundException {
        return productService.addProduct(product, category);
    }


    @GetMapping("/seller/product/{id}")
    public ProductDto getProduct(@PathVariable Long id) throws NotFoundException {
        return productService.getProduct(id);
    }


    @DeleteMapping("/seller/delProduct/{id}")
    public String  deleteProductById(@PathVariable Long id, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        return productService.deleteProductById(id, email);
    }


    @PatchMapping("/seller/updateProduct/{pId}")
    public String updateProductById(@PathVariable Long pId, @RequestBody Product product) throws NotFoundException {
        String email=currentUserService.getUser();
        productService.updateProductByProductId(pId, email, product);
        return "Product is successfully updated";
    }


    @GetMapping("/seller/allProducts")
    public List<ViewProductDto> allProducts(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                            @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) throws NotFoundException {
       return productService.getProductDetails(pageNo, pageSize, sortBy);
    }


}
