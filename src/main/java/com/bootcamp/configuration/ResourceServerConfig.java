package com.bootcamp.configuration;

import com.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    UserService userDetailsService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final LockAuthenticationManager lockAuthenticationManager;

    public ResourceServerConfig(LockAuthenticationManager lockAuthenticationManager) {
        super();
        this.lockAuthenticationManager = lockAuthenticationManager;
    }


//    public ResourceServerConfig() {
//        super();
//    }

//    @Bean
//    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        final  LoginFailedAttempt loginFailedAttempt = new LoginFailedAttempt();
//        loginFailedAttempt.setUserDetailsService(userDetailsService);
//        loginFailedAttempt.setPasswordEncoder(bCryptPasswordEncoder);
//
//
//        return loginFailedAttempt;
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());

        //Adding here new authentication provider
        authenticationManagerBuilder.authenticationProvider(lockAuthenticationManager);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/app/").anonymous()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/seller/**").hasAnyRole("SELLER","ADMIN")
                .antMatchers("/customer/**").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/category/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/category/seller/**").hasAnyRole("ADMIN","SELLER")
                .antMatchers("/category/customer/**").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers("/product/admin").hasAnyRole("ADMIN")
                .antMatchers("/product/seller").hasAnyRole("SELLER")
                .antMatchers("/product/customer").hasAnyRole("CUSTOMER")
                .antMatchers("/cart/**").hasAnyRole("CUSTOMER")
                //.antMatchers("/order/**")
               // .antMatchers("/order/seller/**").hasAnyRole("SELLER")
                .anyRequest().permitAll()//.authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
        ;
    }

}
