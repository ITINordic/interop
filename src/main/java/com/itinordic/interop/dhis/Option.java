package com.itinordic.interop.dhis;

import java.util.Objects;

/**
 *
 * @author Charles Chigoriwa
 */
public class Option {
    
    private String id;
    private String name;
    private String code;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.code);
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
        final Option other = (Option) obj;
        return Objects.equals(this.code, other.code);
    }
   
    

    @Override
    public String toString() {
        return "Option{" + "id=" + id + ", name=" + name + ", code=" + code + '}';
    }
    
    
    
    
    
}
