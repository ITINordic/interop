package com.itinordic.interop.repo;

import com.itinordic.interop.entity.DiagnosisOption;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisOptionRepository extends JpaRepository<DiagnosisOption, UUID>, QuerydslPredicateExecutor {

    public DiagnosisOption findByDhisCode(String dhisCode);
    public DiagnosisOption findByDhisName(String dhisName);
    public DiagnosisOption findByDhisId(String dhisId);

}
