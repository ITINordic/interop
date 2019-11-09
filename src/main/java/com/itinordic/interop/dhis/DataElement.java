package com.itinordic.interop.dhis;

import com.itinordic.interop.dhis.OptionSet;
import com.itinordic.interop.dhis.CategoryCombo;
import java.util.Objects;

/**
 *
 * @author Charles Chigoriwa
 */
public class DataElement {
    
    private String id;
    private String name;
    private String code;
    private String valueType;
    private CategoryCombo categoryCombo;
    private Boolean optionSetValue;
    private OptionSet optionSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public CategoryCombo getCategoryCombo() {
        return categoryCombo;
    }

    public void setCategoryCombo(CategoryCombo categoryCombo) {
        this.categoryCombo = categoryCombo;
    }

    public Boolean getOptionSetValue() {
        return optionSetValue;
    }

    public void setOptionSetValue(Boolean optionSetValue) {
        this.optionSetValue = optionSetValue;
    }

    public OptionSet getOptionSet() {
        return optionSet;
    }

    public void setOptionSet(OptionSet optionSet) {
        this.optionSet = optionSet;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataElement other = (DataElement) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
    

    @Override
    public String toString() {
        return "DataElement{" + "id=" + id + ", name=" + name + ", code=" + code + ", valueType=" + valueType + ", categoryCombo=" + categoryCombo + ", optionSetValue=" + optionSetValue + ", optionSet=" + optionSet + '}';
    }
    
    
    
}
