package com.itinordic.interop.dhis;

/**
 *
 * @author Charles Chigoriwa
 */
public class OrganizationUnit {
    
    private String name;
    private String id;
    private String code;
    private String shortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "OrganizationUnit{" + "name=" + name + ", id=" + id + ", code=" + code + ", shortName=" + shortName + '}';
    }
    
    

   
    
    
    
}
