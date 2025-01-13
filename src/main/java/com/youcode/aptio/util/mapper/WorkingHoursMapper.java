package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import com.youcode.aptio.dto.workingHours.WorkingHoursResponse;
import com.youcode.aptio.model.WorkingHours;
import org.mapstruct.*;

import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkingHoursMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    WorkingHours toWorkingHours(WorkingHoursRequest request);

    @Mapping(target = "businessId", source = "business.id")
    @Mapping(target = "businessName", source = "business.name")
    @Mapping(target = "isOpen", expression = "java(isBusinessOpen(workingHours))")
    WorkingHoursResponse toWorkingHoursResponse(WorkingHours workingHours);

    List<WorkingHoursResponse> toWorkingHoursResponseList(List<WorkingHours> workingHours);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateWorkingHoursFromRequest(WorkingHoursRequest request, @MappingTarget WorkingHours workingHours);

    default boolean isBusinessOpen(WorkingHours workingHours) {
        if (workingHours == null || workingHours.getStartTime() == null || workingHours.getEndTime() == null) {
            return false;
        }

        LocalTime currentTime = LocalTime.now();
        return !currentTime.isBefore(workingHours.getStartTime()) &&
                !currentTime.isAfter(workingHours.getEndTime());
    }
}