package com.bootcamp.service;

import com.bootcamp.entities.order.Cart;
import com.bootcamp.entities.product.Product;
import com.bootcamp.entities.product.ProductVariation;
import com.bootcamp.entities.user.Customer;
import com.bootcamp.exceptions.BadRequestException;
import com.bootcamp.exceptions.NotFoundException;
import com.bootcamp.repository.CartRepository;
import com.bootcamp.repository.CustomerRepository;
import com.bootcamp.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    CartRepository cartRepository;

    public ResponseEntity<String> addProduct(long productVariationId, int quantity) throws RuntimeException {
        String email = currentUserService.getUser();
        Customer customer=customerRepository.findByEmail(email);
        Optional<ProductVariation> productVariation=productVariationRepository.findById(productVariationId);
        if (productVariation.isPresent()){
            ProductVariation p=productVariation.get();
            Product product=p.getProduct();
            if(p.getIsActive()){
               if(!product.getIsDeleted()&&product.getIsActive()) {
                   if(p.getQuantityAvailable()!=0){
                       if(p.getQuantityAvailable()>=quantity) {
                           Optional<Cart> cartP = cartRepository.findByProductVariationIdAndCustomerId(p.getId(),customer.getId());
                           if (cartP.isPresent()) {
                               Cart c = cartP.get();
                               if(p.getQuantityAvailable()>=c.getQuantity()+quantity) {
                                   c.setQuantity(c.getQuantity() + quantity);
                                   cartRepository.save(c);
                                   return new ResponseEntity<>("product quantity updated in the cart", HttpStatus.OK);
                               }
                               else {
                                   throw new BadRequestException("Only "+p.getQuantityAvailable()+" quantity availabe for this product " +
                                           "and "+c.getQuantity()+" is already present in cart");
                               }
                           } else {
                               Cart cart = new Cart();
                               cart.setCustomer(customer);
                               cart.setProductVariation(p);
                               cart.setQuantity(quantity);
                               cart.setWishlistItem(false);
                               cartRepository.save(cart);
                               return new ResponseEntity<>("product added to the cart", HttpStatus.OK);
                           }
                       }
                       else {
                           throw new BadRequestException("Only "+p.getQuantityAvailable()+" quantity availabe for this product");
                       }
                   }
                   else{
                       throw new BadRequestException("Product is out of stock");
                   }

               }
               else{
                   throw new NotFoundException("product not Active or Deleted by seller");
               }
            }
            else{
                throw new BadRequestException("product Variation is not active or discontinued");
            }

        }
        else{
            throw new NotFoundException("product not found");
        }

    }

    public List<Cart> getCart(){
        String email = currentUserService.getUser();
        Customer customer=customerRepository.findByEmail(email);
        List<Cart> cart=cartRepository.findAllByCustomerId(customer.getId());
        if(!cart.isEmpty()){
            return cart;
        }
        else
           throw new NotFoundException("cart is empty");
    }


    public ResponseEntity<String> deleteProductFromCart(long productVariationId){
        String email = currentUserService.getUser();
        Customer customer=customerRepository.findByEmail(email);
        Optional<Cart> cart=cartRepository.findByProductVariationIdAndCustomerId(productVariationId,customer.getId());
        if(cart.isPresent()){
            Cart c=cart.get();
            cartRepository.deleteById(c.getId());
            return new ResponseEntity<>("item deleted successfully from cart",HttpStatus.OK);
        }
        else {
            throw new NotFoundException("enter the valid Id or product is not available in cart");
        }
    }

    public ResponseEntity<String> updateProductFromCart(long productVariationId, int quantity){
        String email = currentUserService.getUser();
        Customer customer=customerRepository.findByEmail(email);
        Optional<ProductVariation> productVariation=productVariationRepository.findById(productVariationId);
        Optional<Cart> cart=cartRepository.findByProductVariationId(productVariationId);
        if (cart.isPresent()) {
            ProductVariation p = productVariation.get();
            Product product = p.getProduct();
            if(quantity==0) {
                cartRepository.deleteByProductVariationId(p.getId());
                return new ResponseEntity<>("product with productId:"+product.getId()+" deleted successfully",HttpStatus.OK);
            }
            else {
                if(p.getQuantityAvailable()>=quantity) {
                    Optional<Cart> cartP = cartRepository.findByProductVariationId(p.getId());
                    if (cartP.isPresent()) {
                        Cart c = cartP.get();
                        if(p.getQuantityAvailable()>=quantity) {
                            if(!(c.getQuantity()==quantity)) {
                                c.setQuantity(quantity);
                                cartRepository.save(c);
                                return new ResponseEntity<>("Quantity of product with productVariationId:" + p.getId() + " is updated to " + quantity + " in Cart", HttpStatus.OK);
                            }
                            else{
                                throw new BadRequestException("already "+quantity+" quantity for this product available in cart");
                            }
                            }
                        else {
                            throw new BadRequestException("Only "+p.getQuantityAvailable()+" quantity availabe for this product " +
                                    "and "+c.getQuantity()+" is already present in cart");
                        }
                    } else {
                        throw new NotFoundException("cart is Empty");
                    }
                }
                else {
                    throw new BadRequestException("Only "+p.getQuantityAvailable()+" quantity availabe for this product");
                }
            }
        }
        else{
            throw new NotFoundException("product not found in cart");
        }
    }


    public ResponseEntity<String> emptyCart(){
        String email = currentUserService.getUser();
        Customer customer=customerRepository.findByEmail(email);
        List<Cart> cart=cartRepository.findAllByCustomerId(customer.getId());
        if(!cart.isEmpty()){
            cartRepository.deleteAllByCustomerId(customer.getId());
            return new ResponseEntity<>("Cart is empty Now",HttpStatus.OK);
        }
        else
            throw new BadRequestException("cart is already empty");
    }

}
