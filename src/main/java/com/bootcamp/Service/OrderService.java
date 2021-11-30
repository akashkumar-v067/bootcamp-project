package com.bootcamp.Service;


import com.bootcamp.Entities.Enum.FromStatus;
import com.bootcamp.Entities.Enum.Payment;
import com.bootcamp.Entities.Enum.ToStatus;
import com.bootcamp.Entities.Order.Cart;
import com.bootcamp.Entities.Order.Order;
import com.bootcamp.Entities.Order.OrderProduct;
import com.bootcamp.Entities.Order.OrderStatus;
import com.bootcamp.Entities.Product.Product;
import com.bootcamp.Entities.Product.ProductVariation;
import com.bootcamp.Entities.User.Address;
import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Exceptions.BadRequestException;
import com.bootcamp.Exceptions.NotFoundException;
import com.bootcamp.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;


    public ResponseEntity<String> orderAllFromCart() {
        String email = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(email);
        List<Cart> c = cartRepository.findAllByCustomerId(customer.getId());
        if (c.isEmpty()) {
            throw new NotFoundException("cart is empty");
        } else {
            double amount = 0;
            for (Cart i : c) {
                ProductVariation productVariation = i.getProductVariation();
                if (productVariation.getProduct().getDeleted()) {
                    throw new BadRequestException("product is deleted or Not available");
                } else {
                    if(!productVariation.getActive()){
                        throw new BadRequestException("product is not active or available");
                    }
                    else {
                        if(productVariation.getQuantityAvailable()==0){
                            throw new NotFoundException("Product is out of stock");
                        }
                        else {
                            if (i.getQuantity() > productVariation.getQuantityAvailable()) {
                                throw new BadRequestException("ONLY " + productVariation.getQuantityAvailable() + " quantity available"+
                                " and Cart contains "+i.getQuantity()+" quantity");
                            }
                        }
                    }
                }
                amount = amount + (productVariation.getPrice() * i.getQuantity());
            }
            Set<Address> addresses = customer.getAddresses();
            if (!addresses.isEmpty()) {
                Address delvAddress = addresses.stream().findFirst().get();
                Order order = new Order();
                order.setCustomer(customer);
                toOrderAddressFromCustomerAddress(delvAddress, order);
                order.setAmountPaid(amount);
                order.setPaymentMethod(Payment.CASH_ON_DELIVERY);
                order.setDateCreated(LocalDateTime.now());

                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setTransitionNotesComments("COD ORDER");
                orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
                orderStatus.setToStatus(ToStatus.ORDER_CONFIRMED);
                orderRepository.save(order);
                for (Cart i : c) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProductVariation(i.getProductVariation());
                    orderProduct.setOrders(order);
                    orderProduct.setQuantity(i.getQuantity());
                    orderProduct.setOrderStatus(orderStatus);
                    orderProductRepository.save(orderProduct);
                   ProductVariation productVariation= i.getProductVariation();
                   productVariation.setQuantityAvailable(productVariation.getQuantityAvailable()
                   - i.getQuantity());
                   productVariationRepository.save(productVariation);
                }
                    cartRepository.deleteAllByCustomerId(customer.getId());
                    return new ResponseEntity<>("Order placed Successfully orderId is " + order.getId(), HttpStatus.OK);

            }
            else {
                throw new NotFoundException("No address found add address to order");
            }

        }


    }



     public void toOrderAddressFromCustomerAddress(Address address,Order order){
        order.setCustomerAddressLine(address.getAddressLine());
        order.setCustomerCity(address.getCity());
        order.setCustomerState(address.getState());
        order.setCustomerCountry(address.getCountry());
        order.setCustomerZipCode(address.getZipCode());
        order.setCustomerAddressLabel(address.getLabel());

     }

     public ResponseEntity<String> orderPartialFromCart(Long[] productVariationIds) {
         String email = currentUserService.getUser();
         Customer customer = customerRepository.findByEmail(email);
         List<Cart> c=new ArrayList<>();
         for(Long i:productVariationIds){
             if(i==null){
                 throw new BadRequestException("productVariationId should not be null or valid");
             }
            Optional<Cart> cart=cartRepository.findByProductVariationIdAndCustomerId(i, customer.getId());
            if(cart.isPresent()){
                c.add(cart.get());
            }
            else{
                throw new NotFoundException("product with productVariationId "+i+" is not present in cart");
            }
         }
         if(c.isEmpty()){
             throw new NotFoundException("productVariationId should not be null or valid");
         }
         double amount = 0;
         for (Cart i : c) {
             ProductVariation productVariation = i.getProductVariation();
             if (!productVariation.getActive()) {
                 throw new BadRequestException("product is not active or availabe");
             } else {
                 if (i.getQuantity() > productVariation.getQuantityAvailable()) {
                     throw new BadRequestException("ONLY " + productVariation.getQuantityAvailable() + " quantity available");
                 }
             }
             amount = amount + (productVariation.getPrice() * i.getQuantity());
         }
         Set<Address> addresses = customer.getAddresses();
         if (!addresses.isEmpty()) {
             Address delvAddress = addresses.stream().findFirst().get();
             Order order = new Order();
             order.setCustomer(customer);
             toOrderAddressFromCustomerAddress(delvAddress, order);
             order.setAmountPaid(amount);
             order.setPaymentMethod(Payment.CASH_ON_DELIVERY);
             order.setDateCreated(LocalDateTime.now());

             OrderStatus orderStatus = new OrderStatus();
             orderStatus.setTransitionNotesComments("COD ORDER");
             orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
             orderStatus.setToStatus(ToStatus.ORDER_CONFIRMED);
             orderRepository.save(order);
             for (Cart i : c) {
                 OrderProduct orderProduct = new OrderProduct();
                 orderProduct.setProductVariation(i.getProductVariation());
                 orderProduct.setQuantity(i.getQuantity());
                 orderProduct.setOrders(order);
                 orderProduct.setOrderStatus(orderStatus);
                 orderProductRepository.save(orderProduct);
                 ProductVariation productVariation= i.getProductVariation();
                 productVariation.setQuantityAvailable(productVariation.getQuantityAvailable()
                         - i.getQuantity());
                 productVariationRepository.save(productVariation);
             }
             cartRepository.deleteAllByCustomerId(customer.getId());
             return new ResponseEntity<>("Order placed Successfully orderId is " + order.getId(), HttpStatus.OK);

         }
         else {
             throw new NotFoundException("No address found add address to order");
         }

     }


     public ResponseEntity<String> directOrder(Long productVariationId, int quantity) {
         String email = currentUserService.getUser();
         Customer customer = customerRepository.findByEmail(email);
         if (quantity == 0) {
             throw new BadRequestException("quantity should not be null or zero");
         } else {
             Optional<ProductVariation> p = productVariationRepository.findById(productVariationId);
             if (p.isPresent()) {
                 ProductVariation productVariation = p.get();
                 Product product = productVariation.getProduct();
                 if (productVariation.getActive() && !product.getDeleted()) {
                     if (productVariation.getQuantityAvailable() == 0) {
                         throw new BadRequestException("Product is out of stock");
                     }
                     if (productVariation.getQuantityAvailable() >= quantity) {
                         Set<Address> addresses = customer.getAddresses();
                         if (!addresses.isEmpty()) {
                             Address delvAddress = addresses.stream().findFirst().get();
                             Order order = new Order();
                             order.setAmountPaid(productVariation.getPrice() * quantity);
                             order.setDateCreated(LocalDateTime.now());
                             order.setCustomer(customer);
                             order.setPaymentMethod(Payment.CASH_ON_DELIVERY);
                             toOrderAddressFromCustomerAddress(delvAddress, order);

                             OrderStatus orderStatus = new OrderStatus();
                             orderStatus.setTransitionNotesComments("COD ORDER");
                             orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
                             orderStatus.setToStatus(ToStatus.ORDER_CONFIRMED);
                             orderRepository.save(order);

                             OrderProduct orderProduct = new OrderProduct();
                             orderProduct.setProductVariation(productVariation);
                             orderProduct.setQuantity(quantity);
                             orderProduct.setOrders(order);
                             orderProduct.setOrderStatus(orderStatus);
                             orderProductRepository.save(orderProduct);
                             productVariation.setQuantityAvailable(productVariation.getQuantityAvailable()
                                     - quantity);
                             productVariationRepository.save(productVariation);

                             return new ResponseEntity<>("Order placed successfully with orderId " + order.getId(), HttpStatus.OK);

                         } else {
                             throw new NotFoundException("No address found to order");
                         }

                     } else {
                         throw new BadRequestException("Only " + productVariation.getQuantityAvailable() + " quantity available to Order");
                     }

                 } else {
                     throw new BadRequestException("product is not active or deleted");
                 }
             } else {
                 throw new NotFoundException("No product associated with given id");
             }

         }

     }

    public ResponseEntity<String> cancelOrder(long orderProductId){
        String email = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(email);
        Optional<OrderProduct> o=orderProductRepository.findById(orderProductId);
        if (o.isPresent()) {
            OrderProduct orderProduct = o.get();
            Order order = orderProduct.getOrders();
            if (order.getCustomer().getId() == customer.getId()) {
                OrderStatus orderStatus = orderProduct.getOrderStatus();
                if (!orderStatus.getFromStatus().equals(FromStatus.CANCELLED)) {
                    if (orderStatus.getFromStatus() == FromStatus.ORDER_CONFIRMED ||
                            orderStatus.getFromStatus() == FromStatus.ORDER_PLACED) {
                        orderStatus.setFromStatus(FromStatus.CANCELLED);
                        orderStatus.setToStatus(ToStatus.REFUND_INITIATED);
                        orderProductRepository.save(orderProduct);
                        return new ResponseEntity<>("Order Cancelled successfully refund initiated to the source", HttpStatus.OK);
                    } else {
                        throw new BadRequestException("Order is not confirmed yet..... wait for few time");
                    }
                } else {
                    throw new BadRequestException("Order is already cancelled");
                }
            }
            else{
                throw new NotFoundException("this order is not associated with this Customer");
            }
        }
        else{
            throw new NotFoundException("No order associated with this ProductId");
        }

    }

    public ResponseEntity<String> returnOrder(long orderProductId){
        String email = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(email);
        Optional<OrderProduct> o=orderProductRepository.findById(orderProductId);
        if (o.isPresent()){
            OrderProduct orderProduct=o.get();
            Order order=orderProduct.getOrders();
            if(order.getCustomer().getId()==customer.getId()) {
                OrderStatus orderStatus = orderProduct.getOrderStatus();
                Product p = orderProduct.getProductVariation().getProduct();
                if (p.getReturnable() && !(p.getReturnable() == null)) {
                    if(orderStatus.getFromStatus()==FromStatus.CANCELLED){
                        throw new BadRequestException("Order is already cancelled");
                    }
                    else{
                        if(orderStatus.getFromStatus()==FromStatus.RETURN_REQUESTED){
                            throw new BadRequestException("Return requested already");
                        }
                        else{
                            orderStatus.setFromStatus(FromStatus.RETURN_REQUESTED);
                            orderStatus.setToStatus(ToStatus.RETURN_APPROVED);
                            orderProductRepository.save(orderProduct);
                            return new ResponseEntity<>("Return request received for Order with orderProductId: "+orderProduct.getId(),HttpStatus.OK);
                        }
                    }

                } else {
                    throw new BadRequestException("Order is non returnable");
                }
            }
            else{
                throw new NotFoundException("this order is not associated with this Customer");
            }
        }
        else{
            throw new NotFoundException("No order associated with this ProductId");
        }
    }








     public Order viewOrderById(long orderId){
         String email = currentUserService.getUser();
         Customer customer = customerRepository.findByEmail(email);
       Optional<Order> order = orderRepository.findByIdAndCustomerId(orderId,customer.getId());
       if(order.isPresent()){
           return order.get();
       }
       else
           throw new NotFoundException("No order associated with this user and orderId");
     }


     public List<Order> viewAllOrder(Integer pageNo, Integer pageSize, String sortBy){
         String email = currentUserService.getUser();
         Customer customer = customerRepository.findByEmail(email);
         Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
         List<Order> order= orderRepository.findAllByCustomerId(customer.getId(),paging);
         if(order.isEmpty()){
             throw new NotFoundException("No order associated with this user and orderId");
         }
         else{
             return order;
         }
     }


     public List<Order> viewAllOrderOfSeller(Integer pageNo, Integer pageSize, String sortBy){
        String email=currentUserService.getUser();
         Seller seller= sellerRepository.findByEmail(email);
         Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
         List<Order> order=new ArrayList<>();
         List<Product> productId=productRepository.findAllBySellerId(seller.getId());
         for(Product i:productId){
             ProductVariation productVariation=productVariationRepository.findByProductId(i.getId());
             List<OrderProduct> orderProducts=orderProductRepository.findAllByProductVariationId(productVariation.getId(),paging);

             for(OrderProduct j:orderProducts) {
                 Order orderFind = j.getOrders();

                     order.add(orderFind);

             }
         }
         if(order.isEmpty()){
             throw new NotFoundException("No product ordered from this seller");
         }
         else{
             List<Order> filterOrder=new ArrayList<>();
             for(int k=pageNo*pageSize;k<pageSize+(pageNo*pageSize);k++) {
                filterOrder.add(order.get(k));
             }

             return filterOrder;
         }


     }

     public ResponseEntity<String> changeStatusOfOrder(long orderProductId,String fromStatus,String toStatus) {
         String email = currentUserService.getUser();
         Seller seller = sellerRepository.findByEmail(email);
         Optional<OrderProduct> o = orderProductRepository.findById(orderProductId);
         if (o.isPresent()) {
             OrderProduct orderProduct = o.get();
             ProductVariation productVariation = orderProduct.getProductVariation();
             Product product = productVariation.getProduct();
             if (product.getSeller().getId() == seller.getId()) {
                 OrderStatus orderStatus = orderProduct.getOrderStatus();
                 if (Objects.equals(orderStatus.getFromStatus().toString(), fromStatus) &&
                         Objects.equals(orderStatus.getToStatus().toString(), toStatus)) {
                     throw new BadRequestException("Status already updated");
                 } else {
                     orderStatus.setFromStatus(FromStatus.valueOf(fromStatus));
                     orderStatus.setToStatus(ToStatus.valueOf(toStatus));
                     orderProductRepository.save(orderProduct);
                     return new ResponseEntity<String>("Order status updated successfully", HttpStatus.OK);
                 }
             } else {
                 throw new NotFoundException("product order in this orderId does not belong to this seller ");
             }
         } else {
             throw new NotFoundException("No order associated for this orderId");
         }
     }



    public List<Order> viewAllOrderThroughAdmin(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<Order> orders=new ArrayList<>();
        Iterable<Order> order=orderRepository.findAll(paging);
        for(Order i:order){
            orders.add(i);
        }
        if(orders.isEmpty()){
            throw new NotFoundException("No Order found");
        }
        else{
            return orders;
        }
    }


    public ResponseEntity<String> changeStatusOfOrderByAdmin(long orderProductId,String fromStatus,String toStatus) {
        Optional<OrderProduct> o = orderProductRepository.findById(orderProductId);
        if (o.isPresent()) {
            OrderProduct orderProduct = o.get();
            ProductVariation productVariation = orderProduct.getProductVariation();
            Product product = productVariation.getProduct();
                OrderStatus orderStatus = orderProduct.getOrderStatus();
                if (Objects.equals(orderStatus.getFromStatus().toString(), fromStatus) &&
                        Objects.equals(orderStatus.getToStatus().toString(), toStatus)) {
                    throw new BadRequestException("Status already updated");
                } else {
                    orderStatus.setFromStatus(FromStatus.valueOf(fromStatus));
                    orderStatus.setToStatus(ToStatus.valueOf(toStatus));
                    orderProductRepository.save(orderProduct);
                    return new ResponseEntity<String>("Order status updated successfully", HttpStatus.OK);
                }
        } else {
            throw new NotFoundException("No order associated for this orderId");
        }
    }


}








