package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.ProgramIndicator;
import com.itinordic.interop.util.ProgramIndicatorList;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class ProgramIndicatorServiceImpl implements ProgramIndicatorService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProgramIndicatorServiceImpl(@Nonnull @Qualifier("immisRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void saveProgramIndicator(ProgramIndicator programIndicator) {
        try {
            String uri = "/programIndicators.json?strategy=CREATE";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ProgramIndicator> entity = new HttpEntity<>(programIndicator, headers);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
            System.out.println(result);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.CONFLICT)) {
                System.out.println("Conflict " + programIndicator);
            } else {
                throw ex;
            }
        }

    }

    @Override
    public ProgramIndicator getIndicatorByName(String name) {
        String uri = "/programIndicators?paging=false&filter=name:eq:{name}";
        ProgramIndicatorList programIndicatorList = restTemplate.getForObject(uri, ProgramIndicatorList.class, name);
        if (programIndicatorList != null && programIndicatorList.getProgramIndicators() != null && !programIndicatorList.getProgramIndicators().isEmpty()) {
            return programIndicatorList.getProgramIndicators().get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public ProgramIndicator getIndicatorByCode(String code) {
        String uri = "/programIndicators?paging=false&filter=name:eq:{code}";
        ProgramIndicatorList programIndicatorList = restTemplate.getForObject(uri, ProgramIndicatorList.class, code);
        if (programIndicatorList != null && programIndicatorList.getProgramIndicators() != null && !programIndicatorList.getProgramIndicators().isEmpty()) {
            return programIndicatorList.getProgramIndicators().get(0);
        } else {
            return null;
        }
    }

}
