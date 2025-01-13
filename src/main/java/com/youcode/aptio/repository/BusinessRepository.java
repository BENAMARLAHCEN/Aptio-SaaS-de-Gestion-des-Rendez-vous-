package com.youcode.aptio.repository;

import com.youcode.aptio.model.Business;
import com.youcode.aptio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByName(String name);

    List<Business> findByOwner(User owner);

    @Query("SELECT b FROM Business b WHERE LOWER(b.name) LIKE LOWER(concat('%', :query, '%')) " +
            "OR LOWER(b.description) LIKE LOWER(concat('%', :query, '%')) " +
            "OR LOWER(b.address) LIKE LOWER(concat('%', :query, '%'))")
    List<Business> searchBusinesses(String query);

    Optional<Business> findByNameIgnoreCase(String name);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Business b " +
            "WHERE b.id = :businessId AND b.owner.id = :userId")
    boolean isUserBusinessOwner(Long businessId, Long userId);
}