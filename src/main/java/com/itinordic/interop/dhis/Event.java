package com.itinordic.interop.dhis;

import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class Event {
    
    private String program;
    private String event;
    private String programStage;
    private String orgUnit;
    private String status;
    private String eventDate;
    private String created;
    private String lastUpdated;
    private String completedDate;
    private String dueDate;   
    private boolean deleted;
    private String attributeCategoryOptions;
    private List<DataValue> dataValues;

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getProgramStage() {
        return programStage;
    }

    public void setProgramStage(String programStage) {
        this.programStage = programStage;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getAttributeCategoryOptions() {
        return attributeCategoryOptions;
    }

    public void setAttributeCategoryOptions(String attributeCategoryOptions) {
        this.attributeCategoryOptions = attributeCategoryOptions;
    }

    public List<DataValue> getDataValues() {
        return dataValues;
    }

    public void setDataValues(List<DataValue> dataValues) {
        this.dataValues = dataValues;
    }

    @Override
    public String toString() {
        return "Event{" + "program=" + program + ", event=" + event + ", programStage=" + programStage + ", orgUnit=" + orgUnit + ", status=" + status + ", eventDate=" + eventDate + ", created=" + created + ", lastUpdated=" + lastUpdated + ", completedDate=" + completedDate + ", dueDate=" + dueDate + ", dataValues=" + dataValues + '}';
    }
    
    
    
}
