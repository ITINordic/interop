package com.itinordic.interop.repo;

import com.itinordic.interop.entity.DiagnosisForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
/**
 *
 * @author Charles Chigoriwa
 */
public interface DiagnosisFormRepository extends JpaRepository<DiagnosisForm, Long>, QuerydslPredicateExecutor {

    public DiagnosisForm findByDhisId(String dhisId);

}
