package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.OrganizationUnit;
import com.itinordic.interop.util.DhisSystem;
import java.util.List;
import javax.annotation.Nonnull;

/**
 *
 * @author Charles Chigoriwa
 */
public interface OrganizationUnitService {

  public List<OrganizationUnit> getOrganizationUnits(@Nonnull DhisSystem dhisSystem);

}
