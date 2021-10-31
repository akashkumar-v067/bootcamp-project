package com.bootcamp;

import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Repository.CustomerRepository;
import com.bootcamp.Repository.SellerRepository;
import com.bootcamp.Repository.TokenRepository;
import com.bootcamp.Repository.UserRepository;
import com.bootcamp.Service.EmailService;
import com.bootcamp.Service.TokenService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class BootcampApplicationTests {

  @Autowired
  UserRepository userRepo;
  @Autowired
	TokenService tokenService;
  @Autowired
  EmailService emailService;
  @Autowired
	CustomerRepository customerRepo;
  @Autowired
	SellerRepository sellerRepo;


	@Test
	void contextLoads() {
	}

	@Ignore
	@Test
	public void test1(){
		tokenService.updateToken("aligarhakash@gmail.com");
		System.out.println("hello");
	}
	@Ignore
	@Test
	public void test2(){
		emailService.sendSimpleMessage("aligarhakash@gmail.com","token","hello this is a test mail");


	}
	@Ignore
	@Test
   public void test3(){


		Seller u= sellerRepo.findByEmail("akashmii2000@gmail.com");
		System.out.println(u);

	}
}