package com.bootcamp.Repository;

import com.bootcamp.Entities.Order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {

    public Optional<Order> findByIdAndCustomerId(long id,long customerId);
    public List<Order> findAllByCustomerId(long customerId, Pageable paging);
}
