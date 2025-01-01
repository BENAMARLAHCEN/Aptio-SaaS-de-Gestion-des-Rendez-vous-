package com.youcode.aptio.service.impl;

import com.youcode.aptio.dto.service.ServiceRequest;
import com.youcode.aptio.dto.service.ServiceResponse;
import com.youcode.aptio.model.Business;
import com.youcode.aptio.model.Service;
import com.youcode.aptio.repository.BusinessRepository;
import com.youcode.aptio.repository.ServiceRepository;
import com.youcode.aptio.service.ServiceService;
import com.youcode.aptio.util.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final BusinessRepository businessRepository;

    @Override
    public ServiceResponse createService(Long businessId, ServiceRequest serviceRequest) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business not found"));
        serviceRepository.findServiceByName(serviceRequest.getName())
                .ifPresent(service -> {
                    throw new RuntimeException("Service already exists");
                });
        Service service = serviceMapper.toService(serviceRequest);
        service.setBusiness(business);
        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

    @Override
    public ServiceResponse updateService(Long serviceId, ServiceRequest serviceRequest) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        serviceMapper.updateServiceFromRequest(serviceRequest, service);
        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

    @Override
    public List<ServiceResponse> getServicesByBusinessId(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business not found"));
        return serviceMapper.toServiceResponseList(serviceRepository.findServiceByBusinessId(business.getId()));
    }

    @Override
    public ServiceResponse getServiceById(Long serviceId) {
        return serviceMapper.toServiceResponse(serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found")));
    }

    @Override
    public void deleteService(Long serviceId) {
        try {
            serviceRepository.deleteById(serviceId);
        } catch (Exception e) {
            throw new RuntimeException("Service not found");
        }
    }

    @Override
    public ServiceResponse getServicesByNames(String serviceName) {
        return serviceMapper.toServiceResponse(serviceRepository.findServiceByName(serviceName)
                .orElseThrow(() -> new RuntimeException("Service not found")));
    }


}
