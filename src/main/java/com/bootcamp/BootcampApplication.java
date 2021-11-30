package com.bootcamp;

import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class BootcampApplication implements ApplicationRunner {
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		User u1=new User();
//		u1.setEmail("admin@gmail.com");
//		u1.setFirstName("admin");
//		u1.setLastName("ok");
//		u1.setPhoneNumber("1234567899");
//		u1.setPassword("ttn");
//		u1.setRetypePassword("ttn");
//		userService.registerAsAdmin(u1);
//
//		Seller s1=new Seller();
//		s1.setEmail("seller@gmail.com");
//		s1.setFirstName("seller");
//		s1.setLastName("ok");
//		s1.setPhoneNumber("1234567898");
//		s1.setPassword("ttn");
//		s1.setRetypePassword("ttn");
//		s1.setCompanyName("to the new");
//		s1.setActive(true);
//		userService.registerAsSeller(s1);
//
//
//		Customer c1=new Customer();
//		c1.setEmail("customer@gmail.com");
//		c1.setFirstName("customer");
//		c1.setLastName("ok");
//		c1.setPhoneNumber("1234567897");
//		c1.setPassword("ttn");
//		c1.setRetypePassword("ttn");
//		c1.setActive(true);
//		c1.setContact("919990102944");
//		userService.registerAsCustomer(c1);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
