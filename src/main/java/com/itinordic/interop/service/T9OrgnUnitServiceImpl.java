package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9OrgnUnitPredicateUtil;
import com.itinordic.interop.criteria.T9OrgnUnitSearchDto;
import com.itinordic.interop.entity.T9OrganizationUnit;
import com.itinordic.interop.repo.T9OrganizationUnitRepository;
import com.querydsl.core.types.Predicate;
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
public class T9OrgnUnitServiceImpl implements T9OrgnUnitService {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private T9OrganizationUnitRepository t9OrganizationUnitRepository;

    @Override
    public Page<T9OrganizationUnit> findT9OrganizationUnits(T9OrgnUnitSearchDto t9OrgnUnitSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber = t9OrgnUnitSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);

        Predicate predicate = T9OrgnUnitPredicateUtil.getPredicate(t9OrgnUnitSearchDto);
        if (predicate != null) {
            return t9OrganizationUnitRepository.findAll(predicate, pageRequest);
        } else {
            return t9OrganizationUnitRepository.findAll(pageRequest);
        }
    }

    @Override
    public long getOrganizationUnitCount(T9OrgnUnitSearchDto t9OrgnUnitSearchDto) {
        if (t9OrgnUnitSearchDto == null) {
            return t9OrganizationUnitRepository.count();
        }
        Predicate predicate = T9OrgnUnitPredicateUtil.getPredicate(t9OrgnUnitSearchDto);
        return t9OrganizationUnitRepository.count(predicate);
    }

}
