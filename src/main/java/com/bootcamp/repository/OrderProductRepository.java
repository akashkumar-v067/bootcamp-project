package com.bootcamp.repository;

import com.bootcamp.entities.order.OrderProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderProductRepository extends PagingAndSortingRepository<OrderProduct,Long> {
    //public Optional<OrderProduct> findByIdAndCustomerId(long id, long customerId);

    public List<OrderProduct> findAllByProductVariationId(long ProductVariationId, Pageable pageable);
}
