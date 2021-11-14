package com.bootcamp.Dao;

import com.bootcamp.Entities.User.Role;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UserDao implements UserDetails {
    @Autowired
    UserRepository userRepo;

    private String username;
    private String password;

    private User user;

    public UserDao(User user) {
        this.user = user;
        this.username=user.getEmail();
        this.password=user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> roleNames =  user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());

       //  roleNames.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return roleNames;
    }

    public void setUsername() {
        this.username = user.getEmail();
    }

    public void setPassword() {
        this.password = user.getPassword();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                "role: "+getAuthorities()+"\n"+
                        isAccountNonExpired()+isAccountNonLocked()+
                '}';
    }
}
