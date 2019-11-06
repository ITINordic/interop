package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisFormService {
    
    public Page<DiagnosisForm> findDiagnosisForms(DiagnosisFormSearchDto diagnosisFormSearchDto, String orderField, boolean desc, Integer pageSize);
}
