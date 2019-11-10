package com.itinordic.interop.dao;

import com.itinordic.interop.util.DataSetValueElement;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DataSetValueDao {

    public List<DataSetValueElement> findDataSetValueElements(String eventPeriod);

    public List<DataSetValueElement> findDataSetValueElements();

    public Long findDataSetValueElementCount(String eventPeriod);

    public Long findDataSetValueElementCount();

    public List<DataSetValueElement> findDataSetValueElements(int pageNumber, int perPage);

    public List<DataSetValueElement> findDataSetValueElements(String eventPeriod, int pageNumber, int perPage);

}
