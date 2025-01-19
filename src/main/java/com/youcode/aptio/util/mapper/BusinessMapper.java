package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.business.BusinessRequest;
import com.youcode.aptio.dto.business.BusinessResponse;
import com.youcode.aptio.dto.business.BusinessUpdateRequest;
import com.youcode.aptio.model.Business;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    Business toBusiness(BusinessRequest businessRequest);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "ownerName", source = "owner.username")
    BusinessResponse toBusinessResponse(Business business);

    void updateBusinessFromRequest(BusinessUpdateRequest businessRequest, @MappingTarget Business business);
}
