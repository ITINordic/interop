package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.OrganizationUnit;
import com.itinordic.interop.util.DhisSystem;
import com.itinordic.interop.util.OrganizationUnitList;
import java.util.List;
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
public class OrganizationUnitServiceImpl implements OrganizationUnitService {

    private final RestTemplate immisRestTemplate;
    private final RestTemplate nhisRestTemplate;

    @Autowired
    public OrganizationUnitServiceImpl(@Nonnull @Qualifier("immisRestTemplate") RestTemplate immisRestTemplate, @Nonnull @Qualifier("nhisRestTemplate") RestTemplate nhisRestTemplate) {
        this.immisRestTemplate = immisRestTemplate;
        this.nhisRestTemplate = nhisRestTemplate;
    }
    
    @Override
    public List<OrganizationUnit> getOrganizationUnits(@Nonnull DhisSystem dhisSystem){
        if(dhisSystem.equals(DhisSystem.IMMIS)){
            return getImmisDhisOrganizationUnits();
        }else{
            return getNhisDhisOrganizationUnits();
        }
    }

   
    private List<OrganizationUnit> getImmisDhisOrganizationUnits() {
        String uri = "/organisationUnits?paging=false&fields=[id,code,name,shortName]";
        OrganizationUnitList organizationUnitList = immisRestTemplate.getForObject(uri, OrganizationUnitList.class);
        return organizationUnitList.getOrganisationUnits();
    }

   
    private List<OrganizationUnit> getNhisDhisOrganizationUnits() {
        String uri = "/organisationUnits?paging=false&fields=[id,code,name,shortName]";
        OrganizationUnitList organizationUnitList = nhisRestTemplate.getForObject(uri, OrganizationUnitList.class);
        return organizationUnitList.getOrganisationUnits();
    }

}
