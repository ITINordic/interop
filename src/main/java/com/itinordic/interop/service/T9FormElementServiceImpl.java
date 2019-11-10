package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9FormElementPredicateUtil;
import com.itinordic.interop.criteria.T9FormElementSearchDto;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.T9FormElementRepository;
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
public class T9FormElementServiceImpl implements T9FormElementService {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private T9FormElementRepository t9FormElementRepository;

    @Override
    public Page<T9FormElement> findT9FormElements(T9FormElementSearchDto t9FormElementSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber = t9FormElementSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);

        Predicate predicate = T9FormElementPredicateUtil.getPredicate(t9FormElementSearchDto);
        if (predicate != null) {
            return t9FormElementRepository.findAll(predicate, pageRequest);
        } else {
            return t9FormElementRepository.findAll(pageRequest);
        }
    }

    @Override
    public long getT9FormElementCount(T9FormElementSearchDto t9FormElementSearchDto) {
        if (t9FormElementSearchDto == null) {
            return t9FormElementRepository.count();
        }
        Predicate predicate = T9FormElementPredicateUtil.getPredicate(t9FormElementSearchDto);
        return t9FormElementRepository.count(predicate);
    }

}
