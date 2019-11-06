package com.itinordic.interop.entity;

import com.itinordic.interop.util.CategoryOptionComboUtility;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Charles Chigoriwa
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"data_element_id", "categoryOptionComboId"})
})
public class T9FormElement extends BaseEntity implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private T9DataElement dataElement;    
    private String categoryOptionComboId;
    @ManyToMany(mappedBy = "formElements")
    private List<DiagnosisForm> diagnosisForms;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T9DataElement getDataElement() {
        return dataElement;
    }

    public void setDataElement(T9DataElement dataElement) {
        this.dataElement = dataElement;
    }

    public String getCategoryOptionComboId() {
        return categoryOptionComboId;
    }

    public void setCategoryOptionComboId(String categoryOptionComboId) {
        this.categoryOptionComboId = categoryOptionComboId;
    }

    public List<DiagnosisForm> getDiagnosisForms() {
        return diagnosisForms;
    }

    public void setDiagnosisForms(List<DiagnosisForm> diagnosisForms) {
        this.diagnosisForms = diagnosisForms;
    }
    
    public String getCategoryOptionComboName(){
        return CategoryOptionComboUtility.getCategoryOptionComboName(categoryOptionComboId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof T9FormElement)) {
            return false;
        }
        T9FormElement other = (T9FormElement) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.itinordic.interop.entity.T9FormElement[ id=" + id + " ]";
    }
    
}
