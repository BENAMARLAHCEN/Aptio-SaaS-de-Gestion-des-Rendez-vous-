package com.aptio.repository;

import com.aptio.model.BusinessSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessSettingsRepository extends JpaRepository<BusinessSettings, Long> {
    // Since we'll have only one settings record, we can use findFirst
    BusinessSettings findFirstByOrderById();
}