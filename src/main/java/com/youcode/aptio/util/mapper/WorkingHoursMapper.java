package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import com.youcode.aptio.dto.workingHours.WorkingHoursResponse;
import com.youcode.aptio.model.WorkingHours;
import org.mapstruct.Mapper;

import java.time.DayOfWeek;

@Mapper(componentModel = "spring")
public class WorkingHoursMapper {
    WorkingHours toWorkingHours(WorkingHoursRequest workingHoursRequest) {
        return WorkingHours.builder()
                .day(DayOfWeek.valueOf(workingHoursRequest.getDay()))
                .startTime(workingHoursRequest.getStartTime())
                .endTime(workingHoursRequest.getEndTime())
                .build();
    }

    WorkingHoursResponse toWorkingHoursResponse(WorkingHours workingHours) {
        return WorkingHoursResponse.builder()
                .id(workingHours.getId())
                .day(workingHours.getDay().toString())
                .startTime(workingHours.getStartTime())
                .endTime(workingHours.getEndTime())
                .businessId(workingHours.getBusiness().getId())
                .build();
    }
}
