package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.util.EventList;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public interface EventService {
    
    public EventList getEventList(int page);
    
    public List<Event> getEvents();
    
    public List<Event> getEvents(String startDate, String endDate);
    
}
