package com.itinordic.interop.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
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
public class ImmisOptionSetRestUtility {
    
    
    public static OptionSet getOptionSet(){
        String uri = "https://dev.itin.africa/immis/api/optionSets/YvokOEEfB4m?fields=id,code,name,options[id,code,name]";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<OptionSet> result = restTemplate.exchange(uri, HttpMethod.GET, entity, OptionSet.class);
        return result.getBody();
    }

    public static void main(String[] args) {
        try {
            try (PrintWriter printWriter = new PrintWriter(new File("output/Diagnosis_OptionSet_Values.csv"))) {
                OptionSet optionSet = getOptionSet();
                if (optionSet != null) {
                    printWriter.print("code,");
                    printWriter.print("name,");
                    printWriter.print("id");
                    printWriter.println();
                    for (Option option : optionSet.getOptions()) {
                        printWriter.print("\""+option.getCode()+"\"");
                        printWriter.print(",");
                        printWriter.print("\""+option.getName()+"\"");
                        printWriter.print(",");
                        printWriter.print(option.getId());
                        printWriter.println();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

}
