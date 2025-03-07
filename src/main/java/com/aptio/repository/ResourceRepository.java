package com.aptio.repository;

import com.aptio.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {
    List<Resource> findByIsAvailable(boolean isAvailable);

    List<Resource> findByType(String type);
}