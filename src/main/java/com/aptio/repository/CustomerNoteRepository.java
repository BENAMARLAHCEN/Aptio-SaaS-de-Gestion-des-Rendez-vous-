package com.aptio.repository;

import com.aptio.model.CustomerNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerNoteRepository extends JpaRepository<CustomerNote, String> {
    List<CustomerNote> findByCustomerIdOrderByCreatedAtDesc(String customerId);
}