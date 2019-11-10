package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DataSetValueSearchDto;
import com.itinordic.interop.util.DataSetValueElement;
import org.springframework.data.domain.Page;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DataSetValueService {
    public Page<DataSetValueElement> getDataSetValueElements(DataSetValueSearchDto searchDto, Integer pageSize);
}
