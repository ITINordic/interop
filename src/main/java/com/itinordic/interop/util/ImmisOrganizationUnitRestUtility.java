package com.itinordic.interop.util;

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
public class ImmisOrganizationUnitRestUtility {

    public static List<OrganizationUnit> getOrganizationUnits() {
        String uri = "https://dev.itin.africa/immis/api/organisationUnits?paging=false&fields=[id,code,name,shortName]";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<OrganizationUnitList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, OrganizationUnitList.class);
        if (result != null && result.getBody() != null) {
            return result.getBody().getOrganisationUnits();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getOrganizationUnits());

    }

}
