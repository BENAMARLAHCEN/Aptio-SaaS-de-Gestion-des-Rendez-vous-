package com.youcode.aptio.repository;

import com.youcode.aptio.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByBusinessId(Long businessId);
    List<Service> findServiceByBusinessName(String businessName);
    List<Service> findServiceByBusinessId(Long businessId);
    List<Service> findActiveServicesByBusinessId(Long businessId);
    boolean existsByNameAndBusinessId(String name, Long businessId);
    Page<Service> findByBusinessId(Long businessId, Pageable pageable);
    Page<Service> findByBusinessIdAndActive(Long businessId, boolean active, Pageable pageable);
    Optional<Service> findServiceByNameAndBusinessName(String serviceName, String businessName);
    Optional<Service> findServiceByName(String serviceName);

    @Query("SELECT s FROM Service s WHERE s.business.id = :businessId " +
            "AND (LOWER(s.name) LIKE LOWER(concat('%', :query, '%')) " +
            "OR LOWER(s.description) LIKE LOWER(concat('%', :query, '%')))")
    Page<Service> searchServices(Long businessId, String query, Pageable pageable);
}