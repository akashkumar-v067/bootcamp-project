package com.bootcamp.Repository;

import com.bootcamp.Entities.Order.Order;
import com.bootcamp.Entities.Order.OrderProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository extends PagingAndSortingRepository<OrderProduct,Long> {
    //public Optional<OrderProduct> findByIdAndCustomerId(long id, long customerId);

    public List<OrderProduct> findAllByProductVariationId(long ProductVariationId, Pageable pageable);
}
