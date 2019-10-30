package com.itinordic.interop.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
public class MappingRestUtility {

    public static void main(String[] args) {
         DataSet dataSet=getDataSet();
         OptionSet optionSet=getOptionSet();
         
         Map<String,DataElement> dataElementMap=new HashMap<>();
         Map<String,Option> optionMap=new HashMap<>();
         
         Map<DataElement,Option> mapped=new HashMap<>();
         
         Set<DataElement> unmappedDataElements=new HashSet<>();
         
         for(DataSetElement dataSetElement: dataSet.getDataSetElements()){
            DataElement dataElement=dataSetElement.getDataElement();
            String mappingName=dataElement.getName().split("-")[1].trim().toLowerCase().replaceAll("\\W+|_", "");
            dataElementMap.put(mappingName, dataElement);
         }
         
         for(Option option:optionSet.getOptions()){
             String mappingName=option.getName().substring(option.getName().indexOf(" ")).trim().toLowerCase().replaceAll("\\W+|_", "");
             optionMap.put(mappingName, option);
         }
         
         for(String key:dataElementMap.keySet()){
             DataElement dataElement=dataElementMap.get(key);
             Option option=optionMap.get(key);
             if(option!=null){
                 mapped.put(dataElement, option);
             }else{
                 unmappedDataElements.add(dataElement);
             }
         }
         
         
         try {
            try (PrintWriter printWriter = new PrintWriter(new File("output/Mapped_DataElement_Option.csv"))) {
              
                    printWriter.print("data_element_id,");
                    printWriter.print("data_element_name,");
                    printWriter.print("option_code,");
                    printWriter.print("option_name");
                    printWriter.println();
                    for (DataElement dataElement:mapped.keySet()) {
                        Option option=mapped.get(dataElement);
                        printWriter.print("\""+dataElement.getId()+"\"");
                        printWriter.print(",");
                        printWriter.print("\""+dataElement.getName()+"\"");
                        printWriter.print(",");
                        printWriter.print("\""+option.getCode()+"\"");
                        printWriter.print(",");
                        printWriter.print("\""+option.getName()+"\"");
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
                    for (DataElement dataElement:unmappedDataElements) {
                        printWriter.print("\""+dataElement.getId()+"\"");
                        printWriter.print(",");
                        printWriter.print("\""+dataElement.getName()+"\"");
                        printWriter.println();
                    }
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
         
        
         
                
    }

    private static DataSet getDataSet() {
        String uri = "https://dev.itin.africa/nhis/api/29/dataSets/G6yc7dpLflo?fields=id,name,code,dataSetElements[dataElement[id,name,code,categoryCombo[id,categories[id,name,code],categoryOptionCombos[id,name,code]]]]";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<DataSet> result = restTemplate.exchange(uri, HttpMethod.GET, entity, DataSet.class);
        return result.getBody();
    }

    private static OptionSet getOptionSet() {
        String uri = "https://dev.itin.africa/immis/api/optionSets/YvokOEEfB4m?fields=id,code,name,options[id,code,name]";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<OptionSet> result = restTemplate.exchange(uri, HttpMethod.GET, entity, OptionSet.class);
        return result.getBody();
    }

}
