package com.itinordic.interop.dhis.service;

/**
 *
 * @author Charles Chigoriwa
 */
public class EventPolledItemRetrieverImpl implements EventPolledItemRetriever{
    
    protected static final String POLL_URI = "/events.json?fields=event,lastUpdated,deleted,storedBy&program={programId}&includeDeleted=true&lastUpdatedStartDate={lastUpdatedStartDate}&lastUpdatedEndDate={lastUpdatedEndDate}&order=lastUpdated:ASC";
    
    
    
    
}
