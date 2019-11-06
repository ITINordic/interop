package com.itinordic.interop.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Charles Chigoriwa
 */
@Entity
public class DiagnosisForm extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable=false)
    private DiagnosisOption diagnosisOption;
    private Integer age;
    private String outcome;    
    @ManyToMany
    private List<T9FormElement> formElements;
    @ManyToOne
    @JoinColumn(nullable=false)
    private T9OrganizationUnit organizationUnit;
    @Column(unique=true, nullable=false)
    private String dhisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public T9OrganizationUnit getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(T9OrganizationUnit organizationUnit) {
        this.organizationUnit = organizationUnit;
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
