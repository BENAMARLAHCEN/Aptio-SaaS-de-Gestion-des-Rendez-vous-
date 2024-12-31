package com.youcode.aptio.service;

import com.youcode.aptio.dto.business.BusinessRequest;
import com.youcode.aptio.dto.business.BusinessResponse;
import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusinessService {
    BusinessResponse createBusiness(BusinessRequest businessRequest);

    BusinessResponse getBusinessById(Long id);

    BusinessResponse getBusinessByName(String name);

    List<BusinessResponse> getAllBusinesses(Pageable pageable);

    BusinessResponse updateBusiness(Long id, BusinessRequest businessRequest);

    void deleteBusiness(Long id);

    BusinessResponse addWorkingHoursToBusiness(Long id, WorkingHoursRequest workingHoursRequest);
}
