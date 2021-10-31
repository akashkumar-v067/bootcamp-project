package com.bootcamp.Controllers;

import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    SellerService sellerService;

//    @GetMapping(path = "/getSeller")
//    public Seller getSeller(){
//       return sellerService.getSeller();
//    }


}
