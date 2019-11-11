package com.itinordic.interop.sync;

import java.util.Date;

/**
 *
 * @author Charles Chigoriwa
 */
public interface EventSyncService {
    
    public void syncEvents();
    
    public void routineSync(Date lastUpdatedStartDate, Date lastUpdatedEndDate);
    
}
