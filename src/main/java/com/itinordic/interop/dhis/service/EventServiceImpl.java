package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.util.DateUtility;
import com.itinordic.interop.util.EventList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class EventServiceImpl implements EventService {

    private final RestTemplate restTemplate;

    @Autowired
    public EventServiceImpl(@Nonnull @Qualifier("immisRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public EventList getEventList(Date lastUpdatedStartDate, int page, Date lastUpdatedEndDate) {
        String uri = "/events?program={program}&totalPages=true&page={page}&includeDeleted=true&lastUpdatedStartDate={lastUpdatedStartDate}&lastUpdatedEndDate={lastUpdatedEndDate}&order=lastUpdated:ASC";
        String program = "SXNeRfGsKcW";
        String strLastUpdatedStartDate = DateUtility.formatToLongDhisDate(lastUpdatedStartDate);
        String strLastUpdatedEndDate = DateUtility.formatToLongDhisDate(lastUpdatedEndDate);
        EventList eventList = restTemplate.getForObject(uri, EventList.class, program, page, strLastUpdatedStartDate,strLastUpdatedEndDate);
        return eventList;
    }

    @Override
    public EventList getEventList(int page) {
        String uri = "/events?program=SXNeRfGsKcW&totalPages=true&page={page}&order=lastUpdated:ASC";
        EventList eventList = restTemplate.getForObject(uri, EventList.class, page);
        return eventList;
    }

    @Override
    public List<Event> getEvents() {
        String uri = "/events?program=SXNeRfGsKcW&paging=false&order=lastUpdated:ASC";
        EventList eventList = restTemplate.getForObject(uri, EventList.class);
        return eventList.getEvents();
    }

    @Override
    public List<Event> getEvents(String startDate, String endDate) {
        String uri = "/events?program=SXNeRfGsKcW&paging=false&startDate={startDate}&endDate={endDate}&order=lastUpdated:ASC";
        EventList eventList = restTemplate.getForObject(uri, EventList.class, startDate, endDate);
        return eventList.getEvents();
    }

}
