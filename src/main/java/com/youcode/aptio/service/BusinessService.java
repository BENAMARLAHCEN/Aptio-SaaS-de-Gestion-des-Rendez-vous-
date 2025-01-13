package com.youcode.aptio.service;

import com.youcode.aptio.dto.business.BusinessRequest;
import com.youcode.aptio.dto.business.BusinessResponse;

import java.util.List;

public interface BusinessService {
    BusinessResponse createBusiness(BusinessRequest request);
    BusinessResponse updateBusiness(Long id, BusinessRequest request);
    void deleteBusiness(Long id);
    BusinessResponse assignOwner(Long businessId, Long userId);
    BusinessResponse getBusinessById(Long id);
    List<BusinessResponse> getAllBusinesses();
    List<BusinessResponse> getMyBusinesses();
    BusinessResponse getBusinessByName(String name);
    List<BusinessResponse> searchBusinesses(String query);
}