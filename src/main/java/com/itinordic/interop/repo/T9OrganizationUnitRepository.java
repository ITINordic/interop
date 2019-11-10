package com.itinordic.interop.repo;

import com.itinordic.interop.entity.T9OrganizationUnit;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
/**
 *
 * @author Charles Chigoriwa
 */
public interface T9OrganizationUnitRepository extends JpaRepository<T9OrganizationUnit, UUID>, QuerydslPredicateExecutor {

    public T9OrganizationUnit findByDhisCode(String dhisCode);
    public T9OrganizationUnit findByDhisName(String dhisName);
    public T9OrganizationUnit findByDhisId(String dhisId);

}
