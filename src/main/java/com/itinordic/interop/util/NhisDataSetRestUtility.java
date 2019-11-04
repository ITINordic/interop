package com.itinordic.interop.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
public class NhisDataSetRestUtility {
    
    
    
    public static DataSet getDataSet(){        
        String uri = "https://dev.itin.africa/nhis/api/29/dataSets/G6yc7dpLflo?fields=id,name,code,dataSetElements[dataElement[id,name,code,categoryCombo[id,categories[id,name,code],categoryOptionCombos[id,name,code]]]]";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<DataSet> result = restTemplate.exchange(uri, HttpMethod.GET, entity, DataSet.class);
        return result.getBody();
    }

    public static void main(String[] args) {

        DataSet dataSet=getDataSet();

        try {
            try (PrintWriter printWriter = new PrintWriter(new File("output/T9_DataSet_Elements_With_Category_Options.csv"))) {
              if (dataSet != null) {
                    printWriter.print("data_element_id,");
                    printWriter.print("data_element_code,");
                    printWriter.print("data_element_name,");
                    printWriter.print("categories,");
                    printWriter.print("category_option_combo_id,");
                    printWriter.print("category_option_combo_name,");
                    printWriter.println();
                    for (DataSetElement dataSetElement : dataSet.getDataSetElements()) {
                        DataElement dataElement = dataSetElement.getDataElement();
                        CategoryCombo categoryCombo = dataElement.getCategoryCombo();
                        if (categoryCombo != null) {
                            List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
                            if (categoryOptionCombos != null && !categoryOptionCombos.isEmpty()) {
                                for (CategoryOptionCombo categoryOptionCombo : categoryOptionCombos) {
                                    printWriter.print(dataElement.getId());
                                    printWriter.print(",");
                                    printWriter.print(dataElement.getCode());
                                    printWriter.print(",");
                                    printWriter.print("\"" + dataElement.getName() + "\"");
                                    printWriter.print(",");
                                    printWriter.print("\"" + categoryCombo.getCategoriesAsString() + "\"");
                                    printWriter.print(",");
                                    printWriter.print(categoryOptionCombo.getId());
                                    printWriter.print(",");
                                    printWriter.print("\"" + categoryOptionCombo.getName() + "\"");
                                    printWriter.println();
                                }
                            }

                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

}
