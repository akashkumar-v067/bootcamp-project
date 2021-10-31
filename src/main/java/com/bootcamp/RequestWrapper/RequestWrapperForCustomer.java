package com.bootcamp.RequestWrapper;

import com.bootcamp.Entities.User.Customer;
import com.bootcamp.Entities.User.User;
import org.springframework.stereotype.Component;

@Component
public class RequestWrapperForCustomer {
    User user;
    Customer customer;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
