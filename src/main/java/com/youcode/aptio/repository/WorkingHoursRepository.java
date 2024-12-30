package com.youcode.aptio.repository;

import com.youcode.aptio.model.Business;
import com.youcode.aptio.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
    List<WorkingHours> findByBusiness(Business business);
}
