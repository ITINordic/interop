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
public class ImmisOptionRestUtility {
    
    
    public static Option getOption(String code){
        String uri = "https://dev.itin.africa/immis/api/options?fields=id,name,code&paging=false&filter=code:eq:"+code;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<OptionList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, OptionList.class);
        OptionList optionList=result.getBody();
        if(optionList!=null && optionList.getOptions()!=null && !optionList.getOptions().isEmpty()){
            return optionList.getOptions().get(0);
        }else{
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getOption("G80"));
    }

}
