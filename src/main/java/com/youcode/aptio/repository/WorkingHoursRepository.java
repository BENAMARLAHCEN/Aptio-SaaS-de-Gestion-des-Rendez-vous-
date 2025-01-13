package com.youcode.aptio.repository;

import com.youcode.aptio.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {

    @Query("SELECT wh FROM WorkingHours wh WHERE wh.business.id = :businessId")
    List<WorkingHours> findByBusinessId(@Param("businessId") Long businessId);

    @Query("SELECT wh FROM WorkingHours wh WHERE wh.business.id = :businessId AND wh.day = :dayOfWeek")
    Optional<WorkingHours> findByBusinessIdAndDayOfWeek(
            @Param("businessId") Long businessId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek
    );

    @Query("SELECT CASE WHEN COUNT(wh) > 0 THEN true ELSE false END FROM WorkingHours wh " +
            "WHERE wh.business.id = :businessId AND wh.day = :dayOfWeek")
    boolean existsByBusinessIdAndDayOfWeek(
            @Param("businessId") Long businessId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek
    );

    @Query("SELECT wh FROM WorkingHours wh WHERE wh.business.owner.id = :ownerId")
    List<WorkingHours> findByBusinessOwnerId(@Param("ownerId") Long ownerId);

    @Query("DELETE FROM WorkingHours wh WHERE wh.business.id = :businessId")
    void deleteAllByBusinessId(@Param("businessId") Long businessId);
}