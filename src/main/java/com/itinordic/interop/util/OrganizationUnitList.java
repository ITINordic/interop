package com.itinordic.interop.util;

import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class OrganizationUnitList extends PageableList{
    
    private List<OrganizationUnit> organisationUnits;

    public List<OrganizationUnit> getOrganisationUnits() {
        return organisationUnits;
    }

    public void setOrganisationUnits(List<OrganizationUnit> organisationUnits) {
        this.organisationUnits = organisationUnits;
    }
    
}
