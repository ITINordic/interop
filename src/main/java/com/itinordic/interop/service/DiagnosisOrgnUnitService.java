package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisOrgnUnitSearchDto;
import com.itinordic.interop.entity.DiagnosisOrganizationUnit;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisOrgnUnitService {
    
    public long getOrganizationUnitCount(DiagnosisOrgnUnitSearchDto diagnosisOrgnUnitSearchDto);
    
    public Page<DiagnosisOrganizationUnit> findDiagnosisOrganizationUnits(DiagnosisOrgnUnitSearchDto diagnosisOrgnUnitSearchDto, String orderField, boolean desc, Integer pageSize);
    
    
}
