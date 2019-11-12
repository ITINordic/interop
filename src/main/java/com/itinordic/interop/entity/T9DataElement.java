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
public class T9DataElement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String dhisId;
    @Column(unique = true, nullable = true)
    private String dhisCode;
    @Column(unique = true, nullable = false)
    private String dhisName;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<DiagnosisOption> options;
    @OneToMany(mappedBy = "dataElement")
    private List<T9FormElement> formElements;

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

    public List<T9FormElement> getFormElements() {
        return formElements;
    }

    public void setFormElements(List<T9FormElement> formElements) {
        this.formElements = formElements;
    }

    public List<DiagnosisOption> getOptions() {
        return options;
    }

    public void setOptions(List<DiagnosisOption> options) {
        this.options = options;
    }

    public boolean hasFormElements() {
        return !GeneralUtility.isEmpty(formElements);
    }

    public boolean hasSingleFormElement() {
        return hasFormElements() && formElements.size() == 1;
    }

    public T9FormElement getFirstFormElement() {
        return hasFormElements() ? formElements.get(0) : null;
    }

    public String getOptionsAsCodes() {
        String string = "";
        if (!GeneralUtility.isEmpty(options)) {
            for (DiagnosisOption option : options) {
                if (!string.isEmpty()) {
                    string += ", ";
                }
                string += option.getDhisCode();
            }
        }
        return string;
    }

    public boolean hasOptions() {
        return !GeneralUtility.isEmpty(options);
    }

    public T9DataElement copy(T9DataElement srcDataElement) {
        this.options = srcDataElement.options;
        return this;
    }

    public String getLinkStatus() {
        return hasOptions() ? "L" : "U";
    }

    public T9FormElement getT9FormElement(String categoryOptionComboId) {
        if (hasFormElements()) {
            for (T9FormElement formElement : formElements) {
                if (formElement.getCategoryOptionComboId().equals(categoryOptionComboId)) {
                    return formElement;
                }
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof T9DataElement)) {
            return false;
        }
        T9DataElement other = (T9DataElement) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.itinordic.interop.entity.T9DataElement[ id=" + id + " ]";
    }

}
