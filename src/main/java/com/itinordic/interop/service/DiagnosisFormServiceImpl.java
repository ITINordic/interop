package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisFormPredicateUtil;
import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.entity.DiagnosisOrganizationUnit;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.entity.T9OrganizationUnit;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.repo.DiagnosisOrganizationUnitRepository;
import com.itinordic.interop.repo.T9OrganizationUnitRepository;
import static com.itinordic.interop.util.DateUtility.parseLongDhisDate;
import com.itinordic.interop.util.GeneralUtility;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Transactional
@Service
public class DiagnosisFormServiceImpl implements DiagnosisFormService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
  
    
    @Transactional
    @Override
    public Page<DiagnosisForm> findDiagnosisForms(DiagnosisFormSearchDto diagnosisFormSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber = diagnosisFormSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);

        Predicate predicate = DiagnosisFormPredicateUtil.getPredicate(diagnosisFormSearchDto);
        if (predicate != null) {
            return diagnosisFormRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisFormRepository.findAll(pageRequest);
        }
    }

    @Override
    public long getDiagnosisFormCount(DiagnosisFormSearchDto diagnosisFormSearchDto) {
        if (diagnosisFormSearchDto == null) {
            return diagnosisFormRepository.count();
        }
        Predicate predicate = DiagnosisFormPredicateUtil.getPredicate(diagnosisFormSearchDto);
        if (predicate == null) {
            return diagnosisFormRepository.count();
        }

        return diagnosisFormRepository.count(predicate);
    }


   

}
