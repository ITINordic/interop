package com.itinordic.interop.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Charles Chigoriwa
 */
@Entity
public class DiagnosisOrganizationUnit extends BaseEntity implements Serializable {

   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true, nullable=false)
    private String dhisId;
    private String dhisName;
    @Column(unique=true, nullable=true)
    private String dhisCode;
    @OneToOne(mappedBy = "mappedOrganizationUnit")
    private T9OrganizationUnit mappedOrganizationUnit;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public String getDhisName() {
        return dhisName;
    }

    public void setDhisName(String dhisName) {
        this.dhisName = dhisName;
    }

    public String getDhisCode() {
        return dhisCode;
    }

    public void setDhisCode(String dhisCode) {
        this.dhisCode = dhisCode;
    }

    public T9OrganizationUnit getMappedOrganizationUnit() {
        return mappedOrganizationUnit;
    }

    public void setMappedOrganizationUnit(T9OrganizationUnit mappedOrganizationUnit) {
        this.mappedOrganizationUnit = mappedOrganizationUnit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DiagnosisOrganizationUnit)) {
            return false;
        }
        DiagnosisOrganizationUnit other = (DiagnosisOrganizationUnit) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.itinordic.interop.entity.DiagnosisOrganizationUnit[ id=" + id + " ]";
    }
    
}
