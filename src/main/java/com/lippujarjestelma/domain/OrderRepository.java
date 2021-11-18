package com.lippujarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//@RepositoryRestResource
public interface OrderRepository extends CrudRepository<OrderDetails, Long> {
	List<OrderDetails> findByOrderId(/* @Param("orderId") */ Long orderId);
}
