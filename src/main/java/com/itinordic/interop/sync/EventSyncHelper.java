package com.itinordic.interop.sync;

import com.itinordic.interop.dhis.Event;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public interface EventSyncHelper {
    
    public void saveBatchOfEvents(List<Event> events);
    
}
