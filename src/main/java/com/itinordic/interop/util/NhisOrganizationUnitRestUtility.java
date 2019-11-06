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
public class NhisOrganizationUnitRestUtility {
    
    
   public static List<OrganizationUnit> getOrganizationUnits() {
        String uri = "https://dev.itin.africa/nhis/api/29/organizationUnits?paging=false&fields=[id,code,name]";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<OrganizationUnitList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, OrganizationUnitList.class);
        if (result != null && result.getBody() != null) {
            return result.getBody().getOrganizationUnits();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getOrganizationUnits());

    }

}
