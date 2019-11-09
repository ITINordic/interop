package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.DataSet;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class DataSetServiceImpl implements DataSetService {

    private final RestTemplate restTemplate;

    @Autowired
    public DataSetServiceImpl(@Nonnull @Qualifier("nhisRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public DataSet getDataSet() {
        String uri = "/dataSets/G6yc7dpLflo?fields=id,name,code,dataSetElements[dataElement[id,name,code,categoryCombo[id,categories[id,name,code],categoryOptionCombos[id,name,code]]]]";
        DataSet dataSet = restTemplate.getForObject(uri, DataSet.class);
        return dataSet;
    }

}
