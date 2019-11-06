package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9OrgnUnitSearchDto;
import com.itinordic.interop.entity.T9OrganizationUnit;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface T9OrgnUnitService {
    
    public Page<T9OrganizationUnit> findT9OrganizationUnits(T9OrgnUnitSearchDto diagnosisOrgnUnitSearchDto, String orderField, boolean desc, Integer pageSize);
}
