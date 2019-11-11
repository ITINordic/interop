package com.itinordic.interop.sync;

import com.itinordic.interop.dhis.Event;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public interface EventSyncService {
    
    public void syncEvents();
    
    public void savePage(List<Event> events);
    
}
