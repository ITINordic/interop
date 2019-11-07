package com.itinordic.interop.util;

import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.entity.T9OrganizationUnit;

/**
 *
 * @author Charles Chigoriwa
 */
public class DataSetValueElement {
    
    private Long count;
    private String eventPeriod;
    private T9FormElement formElement;
    private T9OrganizationUnit organizationUnit;

    public DataSetValueElement() {
    }

    public DataSetValueElement(Long count, String eventPeriod, T9FormElement formElement, T9OrganizationUnit organizationUnit) {
        this.count = count;
        this.eventPeriod = eventPeriod;
        this.formElement = formElement;
        this.organizationUnit = organizationUnit;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

   

    public String getEventPeriod() {
        return eventPeriod;
    }

    public void setEventPeriod(String eventPeriod) {
        this.eventPeriod = eventPeriod;
    }

    public T9FormElement getFormElement() {
        return formElement;
    }

    public void setFormElement(T9FormElement formElement) {
        this.formElement = formElement;
    }

    public T9OrganizationUnit getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(T9OrganizationUnit organizationUnit) {
        this.organizationUnit = organizationUnit;
    }
    
    
    
    
}
