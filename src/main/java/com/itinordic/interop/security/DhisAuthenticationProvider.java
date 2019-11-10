package com.itinordic.interop.security;

import com.itinordic.interop.exceptions.UnauthorizedApiException;
import com.itinordic.interop.exceptions.InteropException;
import com.itinordic.interop.InteropConfig;
import com.itinordic.interop.util.GeneralUtility;
import java.io.IOException;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Charles Chigoriwa
 */
public class DhisAuthenticationProvider implements AuthenticationProvider {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment environment;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            String url = getRootUri() + "/me";
            String basicAuthorization = GeneralUtility.getBasicAuthorization(name, password);
            DhisHttpUtility.httpGet(url, basicAuthorization);
            return DhisAuthentication.valueOf(name);
        } catch (UnauthorizedApiException ex) {
            throw new BadCredentialsException("DHIS2  authentication failed");
        } catch (IOException ex) {
            throw new InteropException(ex);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Nonnull
    public String getRootUri() {
        String endPointUrl = environment.getRequiredProperty("dhis.immis.endpoint.url");
        String apiVersion = environment.getRequiredProperty("dhis.immis.api.version");
        boolean withoutVersion = environment.getRequiredProperty("dhis.immis.api.withoutVersion", Boolean.class);
       return InteropConfig.getRootUri(endPointUrl, withoutVersion, apiVersion);
    }

}
