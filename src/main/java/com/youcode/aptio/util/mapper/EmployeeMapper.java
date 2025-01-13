package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.employee.EmployeeDetailsResponse;
import com.youcode.aptio.dto.employee.EmployeeRequest;
import com.youcode.aptio.dto.employee.EmployeeResponse;
import com.youcode.aptio.model.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "businessId", source = "business.id")
    @Mapping(target = "businessName", source = "business.name")
    EmployeeResponse toEmployeeResponse(Employee employee);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "businessId", source = "business.id")
    @Mapping(target = "businessName", source = "business.name")
    @Mapping(target = "workingDays", ignore = true)  // These will be set manually
    @Mapping(target = "workingHours", ignore = true) // These will be set manually
    EmployeeDetailsResponse toEmployeeDetailsResponse(Employee employee);

    List<EmployeeResponse> toEmployeeResponseList(List<Employee> employees);

    @AfterMapping
    default void setFullName(@MappingTarget EmployeeResponse response) {
        if (response.getFirstName() != null && response.getLastName() != null) {
            String fullName = response.getFirstName() + " " + response.getLastName();
            // You can add this to response if needed
        }
    }

    @AfterMapping
    default void setFullName(@MappingTarget EmployeeDetailsResponse response) {
        if (response.getFirstName() != null && response.getLastName() != null) {
            String fullName = response.getFirstName() + " " + response.getLastName();
            // You can add this to response if needed
        }
    }
}