package com.bootcamp.Controllers;

import com.bootcamp.Entities.Enum.FromStatus;
import com.bootcamp.Entities.Enum.ToStatus;
import com.bootcamp.Entities.Order.Order;
import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;


    //Customer API

    //1. API to order products in the cart
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(path = "/placeOrder")
    public ResponseEntity placeOrder(){
       return orderService.orderAllFromCart();
    }


    //2. API to order partial products from the cart
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(path="/placePartialOrder")
    public ResponseEntity placePartialOrder(@RequestParam("productVariationIds") Long[] productVariationIds){

            return orderService.orderPartialFromCart(productVariationIds);

    }

    //3. API to directly order a product
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(path="/directOrder")
    public ResponseEntity directOrder(@RequestParam("productVariationId") Long productVariationId,
                                      @RequestParam("quantity") int quantity){
        return orderService.directOrder(productVariationId,quantity);
    }

    //4. API to cancel an order
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PatchMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestParam("orderProductId") long orderProductId){
        return orderService.cancelOrder(orderProductId);
    }

    //5. API to return an order
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PatchMapping("/returnOrder")
    public ResponseEntity<String> returnOrder(@RequestParam("orderProductId") long orderProductId){
        return orderService.returnOrder(orderProductId);
    }


    //6. API to view my order
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping(path = "/viewOrder/{orderId}")
    public Order viewOrder(@PathVariable("orderId") long orderId){
       return orderService.viewOrderById(orderId);
    }

    //7. API to list all my orders
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping(path="/viewAllOrder")
    public List<Order> viewAllOrder(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                    @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        return orderService.viewAllOrder(pageNo, pageSize, sortBy);
    }



    // Seller API

    //1. API to list all orders of my products
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/viewAllOrderOfSeller")
    public List<Order> viewAllOrderOfSeller(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                            @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        return orderService.viewAllOrderOfSeller(pageNo, pageSize, sortBy);
    }

    //2. API to change status for an order
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PatchMapping("/changeStatusOfOrder")
    public ResponseEntity<String> changeStatusOfOrder(@RequestParam("orderProductId") long orderProductId,
                                                      @RequestHeader("fromStatus")String fromStatus,
                                                      @RequestHeader("toStatus")String toStatus){
        return orderService.changeStatusOfOrder(orderProductId,fromStatus,toStatus);
    }


    //Admin API

    //1. API to list all orders
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/viewAllOrderThroughAdmin")
    public List<Order> viewAllOrderThroughAdmin(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        return orderService.viewAllOrderThroughAdmin(pageNo, pageSize, sortBy);
    }


    //2. API to change status for an order

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/changeStatusOfOrderByAdmin")
    public ResponseEntity<String> changeStatusOfOrderByAdmin(@RequestParam("orderProductId") long orderProductId,
                                                      @RequestHeader("fromStatus")String fromStatus,
                                                      @RequestHeader("toStatus")String toStatus){
        return orderService.changeStatusOfOrderByAdmin(orderProductId,fromStatus,toStatus);
    }

}
