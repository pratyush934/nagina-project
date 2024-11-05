package com.storeservice.storeservice.repository;

import com.storeservice.storeservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
}
