package com.youcode.aptio.repository;

import com.youcode.aptio.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.business.id = :businessId")
    List<Employee> findByBusinessId(Long businessId);

    @Query("SELECT e FROM Employee e WHERE e.business.id = :businessId AND e.isActive = :isActive")
    List<Employee> findByBusinessIdAndActive(Long businessId, boolean isActive);

    @Query("SELECT e FROM Employee e WHERE e.user.id = :userId")
    Optional<Employee> findByUserId(Long userId);

    @Query("SELECT e FROM Employee e WHERE e.business.id = :businessId AND " +
            "LOWER(e.position) LIKE LOWER(concat('%', :position, '%'))")
    List<Employee> findByBusinessIdAndPosition(Long businessId, String position);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e " +
            "WHERE e.business.id = :businessId AND e.user.id = :userId")
    boolean existsByBusinessIdAndUserId(Long businessId, Long userId);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.business.id = :businessId AND e.isActive = true")
    long countActiveEmployeesByBusinessId(Long businessId);

    @Query("SELECT e FROM Employee e WHERE e.business.owner.id = :ownerId")
    List<Employee> findByBusinessOwnerId(Long ownerId);
}