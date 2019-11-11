/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itinordic.interop.sync;

import com.itinordic.interop.dhis.Event;
import java.util.List;

/**
 *
 * @author developer
 */
public interface EventSyncHelper {
    
    public void saveBatchOfEvents(List<Event> events);
    
}
