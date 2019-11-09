package com.itinordic.interop.util;

import com.itinordic.interop.dhis.DataSet;
import com.itinordic.interop.dhis.DataElement;
import com.itinordic.interop.dhis.DataSetElement;
import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.OptionSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Charles Chigoriwa
 */
public class MappingRestUtility {

    public static MappingResult getMappingResult() {
        DataSet dataSet = NhisDataSetRestUtility.getDataSet();
        OptionSet optionSet = ImmisOptionSetRestUtility.getOptionSet();

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

    protected static void printMappingResult(MappingResult mappingResult) {
        try {
            try (PrintWriter printWriter = new PrintWriter(new File("output/Mapped_DataElement_Option.csv"))) {

                printWriter.print("data_element_id,");
                printWriter.print("data_element_name,");
                printWriter.print("option_code,");
                printWriter.print("option_name");
                printWriter.println();
                for (DataElement dataElement : mappingResult.getMapped().keySet()) {
                    Option option = mappingResult.getMapped().get(dataElement).iterator().next();
                    printWriter.print("\"" + dataElement.getId() + "\"");
                    printWriter.print(",");
                    printWriter.print("\"" + dataElement.getName() + "\"");
                    printWriter.print(",");
                    printWriter.print("\"" + option.getCode() + "\"");
                    printWriter.print(",");
                    printWriter.print("\"" + option.getName() + "\"");
                    printWriter.println();
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

        try {
            try (PrintWriter printWriter = new PrintWriter(new File("output/Unmapped_DataElements.csv"))) {
                printWriter.print("data_element_id,");
                printWriter.print("data_element_name");
                printWriter.println();
                for (DataElement dataElement : mappingResult.getUnmappedDataElements()) {
                    printWriter.print("\"" + dataElement.getId() + "\"");
                    printWriter.print(",");
                    printWriter.print("\"" + dataElement.getName() + "\"");
                    printWriter.println();
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        MappingResult mappingResult = getMappingResult();
        printMappingResult(mappingResult);
    }

}
