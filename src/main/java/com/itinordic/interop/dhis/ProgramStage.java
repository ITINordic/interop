/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itinordic.interop.dhis;

import com.itinordic.interop.util.ProgramStageDataElement;
import java.util.List;

/**
 *
 * @author developer
 */
public class ProgramStage {
    
    private String id;
    private String code;
    private String name;
    private List<ProgramStageDataElement> programStageDataElements;

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

    public List<ProgramStageDataElement> getProgramStageDataElements() {
        return programStageDataElements;
    }

    public void setProgramStageDataElements(List<ProgramStageDataElement> programStageDataElements) {
        this.programStageDataElements = programStageDataElements;
    }

    @Override
    public String toString() {
        return "ProgramStage{" + "id=" + id + ", code=" + code + ", name=" + name + ", programStageDataElements=" + programStageDataElements + '}';
    }
    
    
    
}
