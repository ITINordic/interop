package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9DataElementPredicateUtil;
import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.repo.T9DataElementRepository;
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
public class T9DataElementServiceImpl implements T9DataElementService{
    
    
    @Autowired
    private T9DataElementRepository diagnosisOptionRepository;

    @Override
    public Page<T9DataElement> findT9DataElements(T9DataElementSearchDto dataElementSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber =dataElementSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);
        
        Predicate predicate = T9DataElementPredicateUtil.getPredicate(dataElementSearchDto);
        if (predicate != null) {
            return diagnosisOptionRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisOptionRepository.findAll(pageRequest);
        }
    }
    
     @Override
    public long getT9DataElementCount(T9DataElementSearchDto dataElementSearchDto) {
        Predicate predicate = T9DataElementPredicateUtil.getPredicate(dataElementSearchDto);
        return diagnosisOptionRepository.count(predicate);
    }
    
    
}
