package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DataSetValueSearchDto;
import com.itinordic.interop.dao.DataSetValueDao;
import com.itinordic.interop.util.DataSetValueElement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class DataSetValueServiceImpl implements DataSetValueService {

    @Autowired
    private DataSetValueDao dataSetValueDao;

    @Override
    public Page<DataSetValueElement> getDataSetValueElements(DataSetValueSearchDto searchDto, Integer pageSize) {
        int pageNumber = searchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if (searchDto.hasEventPeriod()) {
            List<DataSetValueElement> content = dataSetValueDao.findDataSetValueElements(searchDto.getEventPeriod(), pageNumber, pageSize);
            long total = dataSetValueDao.findDataSetValueElements(searchDto.getEventPeriod()).size();
            return new PageImpl<>(content, pageRequest, total);

        } else {
            List<DataSetValueElement> content = dataSetValueDao.findDataSetValueElements(pageNumber, pageSize);
            long total = dataSetValueDao.findDataSetValueElements().size();
            return new PageImpl<>(content, pageRequest, total);
        }
    }

}
