package com.itinordic.interop.util;

import com.itinordic.interop.dhis.Event;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class EventList extends PageableList{
    
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
}
