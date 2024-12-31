package com.youcode.aptio.repository;

import com.youcode.aptio.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByName(String name);

    Optional<Business> findByEmail(String email);
    // get all businesses by pagination and sort them by name in ascending order
}
