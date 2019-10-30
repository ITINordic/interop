package com.itinordic.interop.util;

import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class Program {
    
    private String id;
    private String code;
    private String name;
    private List<ProgramStage> programStages;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProgramStage> getProgramStages() {
        return programStages;
    }

    public void setProgramStages(List<ProgramStage> programStages) {
        this.programStages = programStages;
    }

    @Override
    public String toString() {
        return "Program{" + "id=" + id + ", code=" + code + ", name=" + name + ", programStages=" + programStages + '}';
    }
    
    
    
}
