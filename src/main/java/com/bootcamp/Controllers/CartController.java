package com.bootcamp.Controllers;

import com.bootcamp.Entities.Order.Cart;
import com.bootcamp.Exceptions.BadRequestException;
import com.bootcamp.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    // 1. API to add product in cart

    @PostMapping(path = "/addProduct")
    public ResponseEntity addProductToCart(@RequestParam(name = "productVariationId") long productVariationId,
                                           @RequestParam(name = "quantity") int quantity) {
        if (quantity != 0) {
            return cartService.addProduct(productVariationId, quantity);
        } else {
            throw new BadRequestException("quantity should not be zero");
        }
    }

    //2. API to view the cart

    @GetMapping(path = "/getCart")
    public List<Cart> viewCart() {
       return cartService.getCart();

    }

    //3. API to delete product from cart

    @DeleteMapping(path="/deleteProductFromCart")
    public ResponseEntity deleteProductFromCart(@RequestParam("productVariationId") long productVariationId){
       return cartService.deleteProductFromCart(productVariationId);

    }


    //4. API to update product in cart
    @PutMapping(path="/updateProductFromCart")
    public ResponseEntity updateProductFromCart(@RequestParam(name = "productVariationId") long productVariationId,
                                                @RequestParam(name = "quantity") int quantity){
       return cartService.updateProductFromCart(productVariationId,quantity);

    }


    //5. API to Empty the cart

    @DeleteMapping(path="/emptyCart")
    public ResponseEntity emptyCart(){
        return  cartService.emptyCart();
    }




}
