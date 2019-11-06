package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9OrgnUnitPredicateUtil;
import com.itinordic.interop.criteria.T9OrgnUnitSearchDto;
import com.itinordic.interop.entity.T9OrganizationUnit;
import com.itinordic.interop.repo.T9OrganizationUnitRepository;
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
public class T9OrgnUnitServiceImpl implements T9OrgnUnitService{
    
    
    @Autowired
    private T9OrganizationUnitRepository diagnosisOrganizationUnitRepository;

    @Override
    public Page<T9OrganizationUnit> findT9OrganizationUnits(T9OrgnUnitSearchDto diagnosisOrgnUnitSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber =diagnosisOrgnUnitSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);
        
        Predicate predicate = T9OrgnUnitPredicateUtil.getPredicate(diagnosisOrgnUnitSearchDto);
        if (predicate != null) {
            return diagnosisOrganizationUnitRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisOrganizationUnitRepository.findAll(pageRequest);
        }
    }
    
    
}
