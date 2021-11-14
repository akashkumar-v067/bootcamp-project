package com.bootcamp;

import com.bootcamp.Entities.User.User;
import com.bootcamp.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class BootcampApplication implements ApplicationRunner {
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User u1=new User();
		u1.setEmail("admin@gmail.com");
		u1.setFirstName("akash");
		u1.setLastName("kumar");
		u1.setPhoneNumber("1234567899");
		u1.setPassword("admin");
		u1.setRetypePassword("admin");
		userService.registerAsAdmin(u1);
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
