package com.youcode.aptio.service;

import com.youcode.aptio.dto.service.ServiceRequest;
import com.youcode.aptio.dto.service.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceService {
    ServiceResponse createService(Long businessId, ServiceRequest serviceRequest);
    Page<ServiceResponse> getServicesByBusinessId(Long businessId, Pageable pageable);
    Page<ServiceResponse> getActiveServicesByBusiness(Long businessId, Pageable pageable);
    Page<ServiceResponse> searchServices(Long businessId, String query, Pageable pageable);
    ServiceResponse getServiceById(Long serviceId);
    ServiceResponse updateService(Long serviceId, ServiceRequest serviceRequest);
    void deleteService(Long serviceId);
    ServiceResponse toggleServiceStatus(Long serviceId);
}
