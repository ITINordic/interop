package com.itinordic.interop.sync;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.dhis.service.EventService;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.util.EventList;
import com.itinordic.interop.util.Pager;
import java.util.Date;
import java.util.List;
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
public class EventSyncServiceImpl implements EventSyncService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private EventService eventService;

    @Scheduled(fixedDelay = 60000, initialDelay = 60000)
    public void scheduleSyncEvents() {
        syncEvents();
    }

    @Override
    public synchronized void syncEvents() {
        logger.info("Event synchronization started");
        Date maximumDhisLastUpdate = diagnosisFormRepository.getMaximumDhisLastUpdateDate();
        if (maximumDhisLastUpdate == null) {
            initSync();
        } else {
            Date lastUpdatedStartDate = maximumDhisLastUpdate;
            Date lastUpdatedEndDate = new Date();
            routineSync(lastUpdatedStartDate, lastUpdatedEndDate);
        }
        logger.info("Event synchronization end");
    }

    private void initSync() {
        int page = 1;
        Pager pager;
        do {
            EventList eventList = eventService.getEventList(page++);
            pager = eventList.getPager();
            List<Event> events = eventList.getEvents();
            for (Event event : events) {
                DiagnosisForm diagnosisForm = diagnosisFormService.transform(event).orElse(null);
                if (diagnosisForm != null) {
                    diagnosisFormRepository.save(diagnosisForm);
                }

            }

        } while (page <= pager.getPageCount());
    }

    private void routineSync(Date lastUpdatedStartDate, Date lastUpdatedEndDate) {
        int page = 1;
        Pager pager;
        do {
            EventList eventList = eventService.getEventList(lastUpdatedStartDate, page++, lastUpdatedEndDate);
            pager = eventList.getPager();
            List<Event> events = eventList.getEvents();
            for (Event event : events) {
                DiagnosisForm diagnosisForm = diagnosisFormService.transform(event).orElse(null);
                if (diagnosisForm != null) {
                    diagnosisFormRepository.save(diagnosisForm);
                }

            }
        } while (page <= pager.getPageCount());
    }

}
