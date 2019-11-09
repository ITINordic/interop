package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.DataElement;
import com.itinordic.interop.dhis.DataSet;
import com.itinordic.interop.dhis.DataSetElement;
import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.OptionSet;
import com.itinordic.interop.util.MappingResult;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class MappingServiceImpl implements MappingService{
    
    
    @Autowired
    private OptionSetService optionSetService;
    @Autowired
    private DataSetService dataSetService;
    
    
    @Override
     public MappingResult getMappingResult() {
        DataSet dataSet = dataSetService.getDataSet();
        OptionSet optionSet = optionSetService.getOptionSet();

        Map<String, DataElement> dataElementMap = new HashMap<>();
        Map<String, Option> optionMap = new HashMap<>();

        MappingResult mappingResult = new MappingResult();
        mappingResult.setInputDataSet(dataSet);

        for (DataSetElement dataSetElement : dataSet.getDataSetElements()) {
            DataElement dataElement = dataSetElement.getDataElement();
            String mappingName = dataElement.getName().split("-")[1].trim().toLowerCase().replaceAll("\\W+|_", "");
            dataElementMap.put(mappingName, dataElement);
        }

        for (Option option : optionSet.getOptions()) {
            String optionCode = option.getCode();
            String optionName=option.getName().trim();
            String mappingName;
            if (optionName.startsWith(optionCode)) {
                mappingName = optionName.substring(optionName.indexOf(" ")).trim().toLowerCase().replaceAll("\\W+|_", "");
            } else {
                mappingName = optionName;
            }

            optionMap.put(mappingName, option);
        }

        for (String key : dataElementMap.keySet()) {
            DataElement dataElement = dataElementMap.get(key);
            Option option = optionMap.get(key);

            if (option == null && key.contains("beddays")) {
                String noBedDaysKey = key.replaceAll("beddays", "").trim();
                option = optionMap.get(noBedDaysKey);
            }

            if (option != null) {
                mappingResult.addToMapped(dataElement, option);
            } else {
                mappingResult.addToUnmapped(dataElement);
            }
        }

        return mappingResult;

    }
}
