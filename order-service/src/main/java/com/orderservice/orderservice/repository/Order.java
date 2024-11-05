package com.orderservice.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Order extends JpaRepository<Order, String> {
}
