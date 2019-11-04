package com.itinordic.interop.util;

import java.io.Serializable;

/**
 *
 * @author Charles Chigoriwa
 */
public class ProgramIndicator implements Serializable{
    
    private String program;
    private String aggregationType;
    private String analyticsType;
    private String expression;
    private String filter;
    private String shortName;
    private String code;
    private String name;

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getAnalyticsType() {
        return analyticsType;
    }

    public void setAnalyticsType(String analyticsType) {
        this.analyticsType = analyticsType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
    
    
    
}
