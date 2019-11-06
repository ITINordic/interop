package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9FormElementPredicateUtil;
import com.itinordic.interop.criteria.T9FormElementSearchDto;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.T9FormElementRepository;
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
public class T9FormElementServiceImpl implements T9FormElementService{
    
    
    @Autowired
    private T9FormElementRepository diagnosisFormRepository;

    @Override
    public Page<T9FormElement> findT9FormElements(T9FormElementSearchDto diagnosisFormSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber =diagnosisFormSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);
        
        Predicate predicate = T9FormElementPredicateUtil.getPredicate(diagnosisFormSearchDto);
        if (predicate != null) {
            return diagnosisFormRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisFormRepository.findAll(pageRequest);
        }
    }
    
    
}
