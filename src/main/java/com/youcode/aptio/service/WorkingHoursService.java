package com.youcode.aptio.service;

import com.youcode.aptio.dto.workingHours.BusinessHoursResponse;
import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import com.youcode.aptio.dto.workingHours.WorkingHoursResponse;

import java.time.DayOfWeek;
import java.util.List;

public interface WorkingHoursService {
    WorkingHoursResponse createWorkingHours(Long businessId, WorkingHoursRequest request);
    WorkingHoursResponse updateWorkingHours(Long workingHoursId, WorkingHoursRequest request);
    void deleteWorkingHours(Long workingHoursId);
    List<WorkingHoursResponse> getWorkingHoursByBusiness(Long businessId);
    BusinessHoursResponse getBusinessSchedule(Long businessId);
    WorkingHoursResponse getWorkingHoursByDay(Long businessId, DayOfWeek dayOfWeek);
    boolean isBusinessOpenNow(Long businessId);
}