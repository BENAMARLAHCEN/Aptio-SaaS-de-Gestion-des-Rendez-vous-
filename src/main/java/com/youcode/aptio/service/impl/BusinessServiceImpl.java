package com.youcode.aptio.service.impl;

import com.youcode.aptio.dto.business.BusinessRequest;
import com.youcode.aptio.dto.business.BusinessResponse;
import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import com.youcode.aptio.model.Business;
import com.youcode.aptio.repository.BusinessRepository;
import com.youcode.aptio.service.BusinessService;
import com.youcode.aptio.util.mapper.BusinessMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Override
    public BusinessResponse createBusiness(BusinessRequest businessRequest){
        Business business = businessMapper.toBusiness(businessRequest);
        if (businessRepository.findByName(business.getName()).isPresent()){
            throw new RuntimeException("Business with name " + business.getName() + " already exists");
        }
        businessRepository.findByEmail(business.getEmail()).ifPresent(b -> {
            throw new RuntimeException("Business with email " + business.getEmail() + " already exists");
        });
        return businessMapper.toBusinessResponse(businessRepository.save(business));
    }

    @Override
    public BusinessResponse getBusinessById(Long id) {
        return null;
    }

    @Override
    public BusinessResponse getBusinessByName(String name) {
        Business business = businessRepository.findByName(name).orElseThrow(() -> new RuntimeException("Business with name " + name + " not found"));
        return businessMapper.toBusinessResponse(business);
    }

    @Override
    public List<BusinessResponse> getAllBusinesses(Pageable pageable) {
        try {
            Page<Business> businesses = businessRepository.findAll(pageable);
            return businesses.map(businessMapper::toBusinessResponse).getContent();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching businesses");
        }
    }

    @Override
    public BusinessResponse updateBusiness(Long id, BusinessRequest businessRequest) {
        Business business = businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Business with id " + id + " not found"));
        businessRepository.findByName(businessRequest.getName()).ifPresent(b -> {
            if (!b.getId().equals(id)) {
                throw new RuntimeException("Business with name " + businessRequest.getName() + " already exists");
            }
        });
        businessRepository.findByEmail(businessRequest.getEmail()).ifPresent(b -> {
            if (!b.getId().equals(id)) {
                throw new RuntimeException("Business with email " + businessRequest.getEmail() + " already exists");
            }
        });
        businessMapper.updateBusinessFromRequest(businessRequest, business);
        try {
            business = businessRepository.save(business);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while updating business");
        }
        return businessMapper.toBusinessResponse(business);
    }

    @Override
    public void deleteBusiness(Long id) {
        Business business = businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Business with id " + id + " not found"));
        try {
            businessRepository.delete(business);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting business");
        }
    }

    @Override
    public BusinessResponse addWorkingHoursToBusiness(Long id, WorkingHoursRequest workingHoursRequest) {
        return null;
    }

}
