package com.aptio.repository;

import com.aptio.model.ScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, String> {
    List<ScheduleEntry> findByStaffId(String staffId);

    List<ScheduleEntry> findByResourceId(String resourceId);

    List<ScheduleEntry> findByAppointmentId(String appointmentId);

    List<ScheduleEntry> findByDate(LocalDate date);

    List<ScheduleEntry> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<ScheduleEntry> findByType(ScheduleEntry.EntryType type);

    List<ScheduleEntry> findByStatus(ScheduleEntry.EntryStatus status);

    @Query("SELECT e FROM ScheduleEntry e WHERE e.date = :date AND e.staff.id = :staffId")
    List<ScheduleEntry> findByDateAndStaffId(LocalDate date, String staffId);

    @Query("SELECT e FROM ScheduleEntry e WHERE e.date = :date AND e.resource.id = :resourceId")
    List<ScheduleEntry> findByDateAndResourceId(LocalDate date, String resourceId);

    @Query("SELECT e FROM ScheduleEntry e WHERE e.date = :date AND e.staff.id = :staffId " +
            "AND ((e.startTime <= :endTime AND e.endTime >= :startTime))")
    List<ScheduleEntry> findOverlappingEntriesForStaff(LocalDate date, LocalTime startTime, LocalTime endTime, String staffId);

    @Query("SELECT e FROM ScheduleEntry e WHERE e.date = :date AND e.resource.id = :resourceId " +
            "AND ((e.startTime <= :endTime AND e.endTime >= :startTime))")
    List<ScheduleEntry> findOverlappingEntriesForResource(LocalDate date, LocalTime startTime, LocalTime endTime, String resourceId);

    @Query("SELECT e FROM ScheduleEntry e WHERE e.date BETWEEN :startDate AND :endDate AND e.staff.id = :staffId")
    List<ScheduleEntry> findStaffSchedule(LocalDate startDate, LocalDate endDate, String staffId);
}