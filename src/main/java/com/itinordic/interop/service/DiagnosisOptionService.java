package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisOptionSearchDto;
import com.itinordic.interop.entity.DiagnosisOption;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisOptionService {
    
    public Page<DiagnosisOption> findDiagnosisOptions(DiagnosisOptionSearchDto diagnosisOptionSearchDto, String orderField, boolean desc, Integer pageSize);
}
