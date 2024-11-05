package com.storeservice.storeservice.repository;

import com.storeservice.storeservice.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {
}
