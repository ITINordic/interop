package com.itinordic.interop.util;

import com.itinordic.interop.dhis.Program;
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
public class ImmisProgramRestUtility {
    
    
    public static Program getProgram(){
        String uri = "https://dev.itin.africa/immis/api/programs/SXNeRfGsKcW";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Program> result = restTemplate.exchange(uri, HttpMethod.GET, entity, Program.class);
        return result.getBody();
    }

    public static void main(String[] args) {
       System.out.println(getProgram());

    }

}
