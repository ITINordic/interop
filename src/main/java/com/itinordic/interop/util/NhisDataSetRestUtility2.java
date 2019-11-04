package com.itinordic.interop.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author Charles Chigoriwa
 */
public class NhisDataSetRestUtility2 {

    //Data Element Only
    public static void main(String[] args) {
        try {
            try (PrintWriter printWriter = new PrintWriter(new File("output/T9_DataSet_Elements_Only.csv"))) {
                DataSet dataSet = NhisDataSetRestUtility.getDataSet();
                if (dataSet != null) {
                    printWriter.print("data_element_id,");
                    printWriter.print("data_element_code,");
                    printWriter.print("data_element_name");
                    printWriter.println();
                    for (DataSetElement dataSetElement : dataSet.getDataSetElements()) {
                        DataElement dataElement = dataSetElement.getDataElement();

                        printWriter.print(dataElement.getId());
                        printWriter.print(",");
                        printWriter.print(dataElement.getCode());
                        printWriter.print(",");
                        printWriter.print("\"" + dataElement.getName() + "\"");
                        printWriter.println();
                    }

                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }
}
