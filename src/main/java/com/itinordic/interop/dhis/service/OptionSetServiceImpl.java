package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.OptionSet;
import com.itinordic.interop.util.OptionList;
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

    @Override
    public Option getOption(String code) {
        String uri = "/options?fields=id,name,code&paging=false&filter=code:eq:{code}";
        OptionList optionList = restTemplate.getForObject(uri, OptionList.class, code);
        if (optionList != null && optionList.getOptions() != null && !optionList.getOptions().isEmpty()) {
            return optionList.getOptions().get(0);
        } else {
            return null;
        }
    }

}
