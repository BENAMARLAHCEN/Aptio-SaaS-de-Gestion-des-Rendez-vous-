package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.service.ServiceRequest;
import com.youcode.aptio.dto.service.ServiceResponse;
import com.youcode.aptio.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Service toService(ServiceRequest serviceRequest);

    ServiceResponse toServiceResponse(Service service);

    void updateServiceFromRequest(ServiceRequest serviceRequest,@MappingTarget Service service);

    List<ServiceResponse> toServiceResponseList(List<Service> services);
}
