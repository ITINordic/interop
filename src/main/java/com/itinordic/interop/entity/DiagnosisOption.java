package com.itinordic.interop.entity;

import com.itinordic.interop.util.GeneralUtility;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Charles Chigoriwa
 */
@Entity
public class DiagnosisOption extends BaseEntity implements Serializable {

   
    private static final long serialVersionUID = 1L;
   
    @Column(unique=true,nullable=false)
    private String dhisId;
    @Column(unique=true,nullable=false)
    private String dhisCode;
    @Column(unique=true,nullable=false)
    private String dhisName;
    @ManyToMany(mappedBy = "options",fetch = FetchType.EAGER)
    private List<T9DataElement> dataElements;
    @OneToMany(mappedBy = "diagnosisOption")
    private List<DiagnosisForm> diagnosisForms;

   

    public String getDhisId() {
        return dhisId;
    }

    public void setDhisId(String dhisId) {
        this.dhisId = dhisId;
    }

    public String getDhisCode() {
        return dhisCode;
    }

    public void setDhisCode(String dhisCode) {
        this.dhisCode = dhisCode;
    }

    public String getDhisName() {
        return dhisName;
    }

    public void setDhisName(String dhisName) {
        this.dhisName = dhisName;
    }

    public List<T9DataElement> getDataElements() {
        return dataElements;
    }

    public void setDataElements(List<T9DataElement> dataElements) {
        this.dataElements = dataElements;
    }

    public List<DiagnosisForm> getDiagnosisForms() {
        return diagnosisForms;
    }

    public void setDiagnosisForms(List<DiagnosisForm> diagnosisForms) {
        this.diagnosisForms = diagnosisForms;
    }
    
    
    
    public boolean hasDataElements(){
        return !GeneralUtility.isEmpty(dataElements);
    }
    
    public String getLinkStatus(){
        return hasDataElements()? "linked" : "unlinked";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DiagnosisOption)) {
            return false;
        }
        DiagnosisOption other = (DiagnosisOption) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "DiagnosisOption{" + "dhisId=" + dhisId + ", dhisCode=" + dhisCode + ", dhisName=" + dhisName + '}';
    }

   
    
}
