package com.youcode.aptio.repository;

import com.youcode.aptio.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByBusinessId(Long businessId);
    Optional<Service> findServiceByNameAndBusinessName(String serviceName, String businessName);
    Optional<Service> findServiceByName(String serviceName);
    List<Service> findServiceByBusinessName(String businessName);
    List<Service> findServiceByBusinessId(Long businessId);


}