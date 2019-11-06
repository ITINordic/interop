package com.itinordic.interop.service;

import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.entity.T9DataElement;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface T9DataElementService {
    
    public Page<T9DataElement> findT9DataElements(T9DataElementSearchDto diagnosisOptionSearchDto, String orderField, boolean desc, Integer pageSize);
}
