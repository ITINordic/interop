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

}
