package com.bootcamp.Configuration;


import com.bootcamp.Entities.User.User;
import com.bootcamp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;


@Configuration
public class LockAuthenticationManager implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<User> byId = userRepository.findByEmail(email);
        if (byId.isEmpty())
            throw new UsernameNotFoundException("Email is not correct");
          User user = byId.get();

        if (user.getEmail().equals(email) && passwordEncoder.matches(password,user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());
        }
        else {
            int numberOfAttempts = user.getInvalidAttemptCount();
            user.setInvalidAttemptCount(++numberOfAttempts);
            userRepository.save(user);

            if (user.getInvalidAttemptCount() >= 3) {
                user.setLocked(true);
                userRepository.save(user);
                throw new LockedException("User account is locked");
            }
            throw new BadCredentialsException("Password is incorrect");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}