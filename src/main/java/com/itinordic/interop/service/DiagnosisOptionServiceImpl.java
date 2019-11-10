package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisOptionPredicateUtil;
import com.itinordic.interop.criteria.DiagnosisOptionSearchDto;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.querydsl.core.types.Predicate;
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
@Service
public class DiagnosisOptionServiceImpl implements DiagnosisOptionService {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;

    @Override
    public Page<DiagnosisOption> findDiagnosisOptions(DiagnosisOptionSearchDto diagnosisOptionSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber = diagnosisOptionSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);

        Predicate predicate = DiagnosisOptionPredicateUtil.getPredicate(diagnosisOptionSearchDto);
        if (predicate != null) {
            return diagnosisOptionRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisOptionRepository.findAll(pageRequest);
        }
    }

    @Override
    public long getDiagnosisOptionCount(DiagnosisOptionSearchDto diagnosisOptionSearchDto) {
        if (diagnosisOptionSearchDto == null) {
            return diagnosisOptionRepository.count();
        }

        Predicate predicate = DiagnosisOptionPredicateUtil.getPredicate(diagnosisOptionSearchDto);
        return diagnosisOptionRepository.count(predicate);
    }

}
