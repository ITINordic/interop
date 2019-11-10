package com.itinordic.interop.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Charles Chigoriwa
 */
@Entity
public class T9OrganizationUnit extends BaseEntity implements Serializable {

   
    private static final long serialVersionUID = 1L;
    
    @Column(unique=true, nullable=false)
    private String dhisId;
    private String dhisName;
    @Column(unique=true, nullable=true)
    private String dhisCode;
    private String dhisShortName;


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

    public String getDhisShortName() {
        return dhisShortName;
    }

    public void setDhisShortName(String dhisShortName) {
        this.dhisShortName = dhisShortName;
    }
    
    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof T9OrganizationUnit)) {
            return false;
        }
        T9OrganizationUnit other = (T9OrganizationUnit) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.itinordic.interop.entity.T9OrganizationUnit[ id=" + id + " ]";
    }
    
}
