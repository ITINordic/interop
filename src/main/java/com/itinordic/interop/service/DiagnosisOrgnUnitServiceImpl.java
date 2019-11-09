package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisOrgnUnitPredicateUtil;
import com.itinordic.interop.criteria.DiagnosisOrgnUnitSearchDto;
import com.itinordic.interop.criteria.T9DataElementPredicateUtil;
import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.entity.DiagnosisOrganizationUnit;
import com.itinordic.interop.repo.DiagnosisOrganizationUnitRepository;
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
public class DiagnosisOrgnUnitServiceImpl implements DiagnosisOrgnUnitService {

    @Autowired
    private DiagnosisOrganizationUnitRepository diagnosisOrganizationUnitRepository;

    @Override
    public Page<DiagnosisOrganizationUnit> findDiagnosisOrganizationUnits(DiagnosisOrgnUnitSearchDto diagnosisOrgnUnitSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber = diagnosisOrgnUnitSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);

        Predicate predicate = DiagnosisOrgnUnitPredicateUtil.getPredicate(diagnosisOrgnUnitSearchDto);
        if (predicate != null) {
            return diagnosisOrganizationUnitRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisOrganizationUnitRepository.findAll(pageRequest);
        }
    }

    @Override
    public long getOrganizationUnitCount(DiagnosisOrgnUnitSearchDto diagnosisOrgnUnitSearchDto) {
        if (diagnosisOrgnUnitSearchDto == null) {
            return diagnosisOrganizationUnitRepository.count();
        }
        Predicate predicate = DiagnosisOrgnUnitPredicateUtil.getPredicate(diagnosisOrgnUnitSearchDto);
        return diagnosisOrganizationUnitRepository.count(predicate);
    }

}
