package com.itinordic.interop.sync;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.dhis.service.EventService;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.util.EventList;
import com.itinordic.interop.util.Pager;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class LiteEventSyncServiceImpl implements LiteEventSyncService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventSyncHelper eventSyncHelper;

    @Scheduled(fixedDelay = 60_000, initialDelay = 60_000)
    public void scheduleSyncEvents() {
       syncEvents();
    }

    @Override
    public synchronized void syncEvents() {
        logger.info("Lite Event Synchronization started");
        Date lastUpdatedStartDate;
        Date lastUpdatedEndDate;
        Date maximumDhisLastUpdate = diagnosisFormRepository.getMaximumDhisLastUpdateDate();
        if (maximumDhisLastUpdate == null) {
            lastUpdatedStartDate = DateTime.now().minusYears(100).toDate();
        } else {
            int toleranceMillis = 2000;
            lastUpdatedStartDate = new DateTime(maximumDhisLastUpdate).minusMillis(toleranceMillis).toDate();
        }
        lastUpdatedEndDate = new Date();
        routineSync(lastUpdatedStartDate, lastUpdatedEndDate);
        logger.info("Lite Event Synchronization ended");
    }

    private void routineSync(Date lastUpdatedStartDate, Date lastUpdatedEndDate) {
        int page = 1;
        Pager pager;
        do {
            EventList eventList = eventService.getEventList(lastUpdatedStartDate, page, lastUpdatedEndDate);
            pager = eventList.getPager();
            List<Event> events = eventList.getEvents();
            eventSyncHelper.saveBatchOfEvents(events);
        } while (++page <= pager.getPageCount());
    }


}
