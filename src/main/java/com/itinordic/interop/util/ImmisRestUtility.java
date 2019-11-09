package com.itinordic.interop.util;

import com.itinordic.interop.dhis.Program;
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
public class ImmisRestUtility {

    public static void main(String[] args) {

        String uri = "https://dev.itin.africa/immis/api/programs/SXNeRfGsKcW?fields=programStages[name,programStageDataElements[dataElement[name,id,valueType,optionSetValue,optionSet[id,options[code,name]],categoryCombo[id,categories[id,name,code],categoryOptionCombos[id,name,code]]]]";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Program> result = restTemplate.exchange(uri, HttpMethod.GET, entity, Program.class);

        System.out.println(result);

    }

}
