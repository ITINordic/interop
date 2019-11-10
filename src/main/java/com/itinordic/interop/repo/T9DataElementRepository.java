package com.itinordic.interop.repo;

import com.itinordic.interop.entity.T9DataElement;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
/**
 *
 * @author Charles Chigoriwa
 */
public interface T9DataElementRepository extends JpaRepository<T9DataElement, UUID>, QuerydslPredicateExecutor {

    public T9DataElement findByDhisCode(String dhisCode);
    public T9DataElement findByDhisName(String dhisName);
    public T9DataElement findByDhisId(String dhisId);

}
