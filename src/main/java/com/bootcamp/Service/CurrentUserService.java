package com.bootcamp.Service;

import com.bootcamp.Dao.UserDao;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Exceptions.UserNotFoundException;
import com.bootcamp.Repository.UserRepository;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    BCryptPasswordEncoder encoder;

//    public String loginUser(String username, String password) {
//        String password1=encoder.encode(password);
//        if (userRepo.checkEmail(username)) {
//            User user = userRepo.findByEmail(username);
//            if(user.getPassword().equals(password1)){
//               String accessToken= UUID.randomUUID().toString();
//                user.setLoggedIn(true);
//                user.setAccessToken(accessToken);
//                userRepo.save(user);
//                return "User logged in access token is:"+accessToken;
//            }
//        }
//        else {
//            throw new UserNotFoundException("username does not exist");
//        }
//       throw new BeanCreationException("credential not valid");
//    }










    public String getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        User user = null;
        if (principal instanceof UserDetails)
        {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;

    }
}

