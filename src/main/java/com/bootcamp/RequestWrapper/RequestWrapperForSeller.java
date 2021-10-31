package com.bootcamp.RequestWrapper;

import com.bootcamp.Entities.User.Seller;
import com.bootcamp.Entities.User.User;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class RequestWrapperForSeller {
    @Valid
    User user;

    Seller seller;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
