package com.itinordic.interop.repo;

import com.itinordic.interop.entity.DiagnosisOrganizationUnit;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisOrganizationUnitRepository extends JpaRepository<DiagnosisOrganizationUnit, UUID>, QuerydslPredicateExecutor {

    public DiagnosisOrganizationUnit findByDhisCode(String dhisCode);

    public DiagnosisOrganizationUnit findByDhisName(String dhisName);

    public DiagnosisOrganizationUnit findByDhisId(String dhisId);

}
