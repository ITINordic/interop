package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.Program;
import java.util.Arrays;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProgramServiceImpl(@Nonnull @Qualifier("immisRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Program getProgram() {
        String uri = "/programs/SXNeRfGsKcW";
        Program program = restTemplate.getForObject(uri, Program.class);
        return program;
    }
}
