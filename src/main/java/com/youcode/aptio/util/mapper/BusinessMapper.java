package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.business.BusinessRequest;
import com.youcode.aptio.dto.business.BusinessResponse;
import com.youcode.aptio.model.Business;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    Business toBusiness(BusinessRequest businessRequest);
    BusinessResponse toBusinessResponse(Business business);

    // add a method to update business
    // Business updateBusiness(Business business, BusinessRequest businessRequest);
    void updateBusinessFromRequest(BusinessRequest businessRequest, @MappingTarget Business business);
}
