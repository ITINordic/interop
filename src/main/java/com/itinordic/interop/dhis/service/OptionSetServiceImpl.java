package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.OptionSet;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class OptionSetServiceImpl implements OptionSetService {

    private final RestTemplate restTemplate;

    @Autowired
    public OptionSetServiceImpl(@Nonnull @Qualifier("immisRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OptionSet getOptionSet() {
        String uri = "/optionSets/YvokOEEfB4m?fields=id,code,name,options[id,code,name]";
        OptionSet optionSet = restTemplate.getForObject(uri, OptionSet.class);
        return optionSet;
    }

}
