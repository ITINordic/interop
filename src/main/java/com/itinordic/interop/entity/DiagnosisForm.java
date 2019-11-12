package com.itinordic.interop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Charles Chigoriwa
 */
@Entity
public class DiagnosisForm extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @ManyToOne
    @JoinColumn(nullable = false)
    private DiagnosisOption diagnosisOption;
    private Integer age;
    private String outcome;
    @ManyToMany
    private List<T9FormElement> formElements;
    @ManyToOne
    @JoinColumn(nullable = false)
    private DiagnosisOrganizationUnit diagnosisOrgUnit;
    @ManyToOne
    @JoinColumn(nullable = true)
    private T9OrganizationUnit t9OrgUnit;
    @Column(unique = true, nullable = false)
    private String dhisId;
    @Column(nullable = false)
    private String eventPeriod;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dhisEventDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dhisCreated;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dhisLastUpdated;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dhisCompletedDate;
    @Column(nullable=true)
    private String status;


    public DiagnosisOption getDiagnosisOption() {
        return diagnosisOption;
    }

    public void setDiagnosisOption(DiagnosisOption diagnosisOption) {
        this.diagnosisOption = diagnosisOption;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public List<T9FormElement> getFormElements() {
        return formElements;
    }

    public void setFormElements(List<T9FormElement> formElements) {
        this.formElements = formElements;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public DiagnosisOrganizationUnit getDiagnosisOrgUnit() {
        return diagnosisOrgUnit;
    }

    public void setDiagnosisOrgUnit(DiagnosisOrganizationUnit diagnosisOrgUnit) {
        this.diagnosisOrgUnit = diagnosisOrgUnit;
    }

    public T9OrganizationUnit getT9OrgUnit() {
        return t9OrgUnit;
    }

    public void setT9OrgUnit(T9OrganizationUnit t9OrgUnit) {
        this.t9OrgUnit = t9OrgUnit;
    }

    public String getEventPeriod() {
        return eventPeriod;
    }

    public void setEventPeriod(String eventPeriod) {
        this.eventPeriod = eventPeriod;
    }

    public Date getDhisEventDate() {
        return dhisEventDate;
    }

    public void setDhisEventDate(Date dhisEventDate) {
        this.dhisEventDate = dhisEventDate;
    }

    public Date getDhisCreated() {
        return dhisCreated;
    }

    public void setDhisCreated(Date dhisCreated) {
        this.dhisCreated = dhisCreated;
    }

    public Date getDhisLastUpdated() {
        return dhisLastUpdated;
    }

    public void setDhisLastUpdated(Date dhisLastUpdated) {
        this.dhisLastUpdated = dhisLastUpdated;
    }

    public Date getDhisCompletedDate() {
        return dhisCompletedDate;
    }

    public void setDhisCompletedDate(Date dhisCompletedDate) {
        this.dhisCompletedDate = dhisCompletedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DiagnosisForm)) {
            return false;
        }
        DiagnosisForm other = (DiagnosisForm) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.itinordic.interop.entity.DiagnosisForm[ id=" + id + " ]";
    }

}
