package com.youcode.aptio.service;

import com.youcode.aptio.dto.service.ServiceRequest;
import com.youcode.aptio.dto.service.ServiceResponse;

import java.util.List;

public interface ServiceService {
    ServiceResponse createService(Long businessId, ServiceRequest serviceRequest);
    List<ServiceResponse> getServicesByBusinessId(Long businessId);
    ServiceResponse getServiceById(Long serviceId);
    void deleteService(Long serviceId);
    ServiceResponse getServicesByNames(String serviceName);
    ServiceResponse updateService(Long serviceId, ServiceRequest serviceRequest);
}
