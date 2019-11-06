package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisFormPredicateUtil;
import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class DiagnosisFormServiceImpl implements DiagnosisFormService{
    
    
    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;

    @Override
    public Page<DiagnosisForm> findDiagnosisForms(DiagnosisFormSearchDto diagnosisFormSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber =diagnosisFormSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);
        
        Predicate predicate = DiagnosisFormPredicateUtil.getPredicate(diagnosisFormSearchDto);
        if (predicate != null) {
            return diagnosisFormRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisFormRepository.findAll(pageRequest);
        }
    }
    
    
}
