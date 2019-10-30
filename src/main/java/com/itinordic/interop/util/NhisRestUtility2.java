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
public class NhisRestUtility2 {

    //Data Element Only
    public static void main(String[] args) {

        String uri = "https://dev.itin.africa/nhis/api/29/dataSets/G6yc7dpLflo?fields=id,name,code,dataSetElements[dataElement[id,name,code,categoryCombo[id,categories[id,name,code],categoryOptionCombos[id,name,code]]]]";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<DataSet> result = restTemplate.exchange(uri, HttpMethod.GET, entity, DataSet.class);

        System.out.println(result);

        try {
            try (PrintWriter printWriter = new PrintWriter(new File("output/T9_DataSet_Elements_Only.csv"))) {
                DataSet dataSet = result.getBody();
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

    public static void main2(String[] args) {

        String uri = "https://dev.itin.africa/nhis/api/29/dataSets/G6yc7dpLflo?fields=id,name,code,dataSetElements[dataElement[id,name,code,categoryCombo[id,categories[id,name,code],categoryOptionCombos[id,name,code]]]]";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<DataSet> result = restTemplate.exchange(uri, HttpMethod.GET, entity, DataSet.class);

        System.out.println(result);
    }

}
