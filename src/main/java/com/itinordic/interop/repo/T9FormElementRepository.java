package com.itinordic.interop.repo;

import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.entity.T9FormElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
/**
 *
 * @author Charles Chigoriwa
 */
public interface T9FormElementRepository extends JpaRepository<T9FormElement, Long>, QuerydslPredicateExecutor {

    public T9FormElement findByDataElementAndCategoryOptionComboId(T9DataElement dataElement,String categoryOptionComboId);

}
