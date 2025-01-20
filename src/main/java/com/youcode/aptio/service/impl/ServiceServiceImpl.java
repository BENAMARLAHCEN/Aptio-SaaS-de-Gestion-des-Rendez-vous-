package com.youcode.aptio.service.impl;

import com.youcode.aptio.dto.service.ServiceRequest;
import com.youcode.aptio.dto.service.ServiceResponse;
import com.youcode.aptio.exception.ResourceNotFoundException;
import com.youcode.aptio.exception.ServiceAlreadyExistsException;
import com.youcode.aptio.model.Business;
import com.youcode.aptio.model.Service;
import com.youcode.aptio.repository.BusinessRepository;
import com.youcode.aptio.repository.ServiceRepository;
import com.youcode.aptio.service.ServiceService;
import com.youcode.aptio.util.mapper.ServiceMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final BusinessRepository businessRepository;

    @Override
    @Transactional
    public ServiceResponse createService(Long businessId, ServiceRequest serviceRequest) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + businessId));

        serviceRepository.findServiceByNameAndBusinessName(serviceRequest.getName(), business.getName())
                .ifPresent(service -> {
                    throw new ServiceAlreadyExistsException("Service with name " + serviceRequest.getName() +
                            " already exists for this business");
                });

        validateServiceRequest(serviceRequest);

        Service service = serviceMapper.toService(serviceRequest);
        service.setBusiness(business);
        service.onCreate();

        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

    @Override
    @Transactional
    public Page<ServiceResponse> getServicesByBusinessId(Long businessId, Pageable pageable) {
        if (!businessRepository.existsById(businessId)) {
            throw new ResourceNotFoundException("Business not found with id: " + businessId);
        }
        return serviceRepository.findByBusinessId(businessId, pageable)
                .map(serviceMapper::toServiceResponse);
    }

    @Override
    @Transactional
    public Page<ServiceResponse> getActiveServicesByBusiness(Long businessId, Pageable pageable) {
        if (!businessRepository.existsById(businessId)) {
            throw new ResourceNotFoundException("Business not found with id: " + businessId);
        }
        return serviceRepository.findByBusinessIdAndActive(businessId, true, pageable)
                .map(serviceMapper::toServiceResponse);
    }

    @Override
    @Transactional
    public Page<ServiceResponse> searchServices(Long businessId, String query, Pageable pageable) {
        if (!businessRepository.existsById(businessId)) {
            throw new ResourceNotFoundException("Business not found with id: " + businessId);
        }
        return serviceRepository.searchServices(businessId, query, pageable)
                .map(serviceMapper::toServiceResponse);
    }

    @Override
    @Transactional
    public ServiceResponse getServiceById(Long serviceId) {
        return serviceMapper.toServiceResponse(serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId)));
    }

    @Override
    @Transactional
    public ServiceResponse updateService(Long serviceId, ServiceRequest serviceRequest) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));

        if (!service.getName().equals(serviceRequest.getName())) {
            serviceRepository.findServiceByNameAndBusinessName(
                    serviceRequest.getName(),
                    service.getBusiness().getName()
            ).ifPresent(existingService -> {
                throw new ServiceAlreadyExistsException("Service with name " + serviceRequest.getName() +
                        " already exists for this business");
            });
        }

        validateServiceRequest(serviceRequest);
        serviceMapper.updateServiceFromRequest(serviceRequest, service);
        service.onUpdate();

        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

    @Override
    @Transactional
    public void deleteService(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));

        if (!service.getAppointments().isEmpty()) {
            throw new IllegalStateException("Cannot delete service with existing appointments");
        }

        serviceRepository.delete(service);
    }

    @Override
    @Transactional
    public ServiceResponse toggleServiceStatus(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));
        service.setActive(!service.isActive());
        service.onUpdate();
        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

    private void validateServiceRequest(ServiceRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            errors.add("Service name is required");
        }

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            errors.add("Service description is required");
        }

        if (request.getDuration() <= 0) {
            errors.add("Service duration must be greater than 0 minutes");
        }

        if (request.getPrice() < 0) {
            errors.add("Service price cannot be negative");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Invalid service request: " + String.join(", ", errors));
        }
    }
}