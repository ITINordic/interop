package com.itinordic.interop;

import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
@Configuration
@EnableScheduling
public class InteropConfig {
    
    @Autowired
    private Environment environment;

    @Bean
    public RestTemplate immisRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        String endPointUrl=environment.getRequiredProperty("dhis.immis.endpoint.url");
        String apiVersion=environment.getRequiredProperty("dhis.immis.api.version");
        boolean withoutVersion=environment.getRequiredProperty("dhis.immis.api.withoutVersion", Boolean.class);
        String rootUri=getRootUri(endPointUrl, withoutVersion, apiVersion);
        String username=environment.getRequiredProperty("dhis.immis.username");
        String password=environment.getRequiredProperty("dhis.immis.password");
        return restTemplateBuilder.rootUri(rootUri).basicAuthentication(username, password).build();
    }
    
    @Bean
    public RestTemplate nhisRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        String endPointUrl=environment.getRequiredProperty("dhis.nhis.endpoint.url");
        String apiVersion=environment.getRequiredProperty("dhis.nhis.api.version");
        boolean withoutVersion=environment.getRequiredProperty("dhis.nhis.api.withoutVersion",Boolean.class);
        String rootUri=getRootUri(endPointUrl, withoutVersion, apiVersion);
        String username=environment.getRequiredProperty("dhis.nhis.username");
        String password=environment.getRequiredProperty("dhis.nhis.password");
        return restTemplateBuilder.rootUri(rootUri).basicAuthentication(username, password).build();
    }

    @Nonnull
    public static String getRootUri(String endpointUrl, boolean withoutVersion,String apiVersion) {
        if (withoutVersion) {
            return endpointUrl + "/api";
        }
        return endpointUrl + "/api/" + apiVersion;
    }

}
