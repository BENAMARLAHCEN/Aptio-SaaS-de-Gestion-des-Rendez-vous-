package com.youcode.aptio.repository;

import com.youcode.aptio.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //public class Employee {
    //    @Id
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //    @Column(nullable = false)
    //    private Long id;
    //
    //    private String position;
    //    boolean isActive;
    //
    //    @OneToOne
    //    @JoinColumn(name = "user_id")
    //    private User user;
    //
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "business_id")
    //    private Business business;
    //
    //}

    Optional<Employee> findByUserId(Long userId);
    List<Employee> findByBusinessId(Long businessId);
    List<Employee> findByBusinessIdAndIsActive(Long businessId, boolean isActive);
    List<Employee> findByBusinessIdAndPosition(Long businessId, String position);
    //
}
