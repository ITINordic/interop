package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9FormElementSearchDto;
import com.itinordic.interop.entity.T9FormElement;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface T9FormElementService {
    
    public long getT9FormElementCount(T9FormElementSearchDto t9FormElementSearchDto);
    
    public Page<T9FormElement> findT9FormElements(T9FormElementSearchDto diagnosisOptionSearchDto, String orderField, boolean desc, Integer pageSize);
}
