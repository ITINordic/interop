package com.itinordic.interop.util;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Charles Chigoriwa
 */
public class ImmisEventRestUtility {
    
    
    public static EventList getEventList(int page) {
        String uri = "https://dev.itin.africa/immis/api/events?program=SXNeRfGsKcW&totalPages=true&page="+page;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<EventList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, EventList.class);
        EventList eventList = result.getBody();
        return eventList;
    }

    public static List<Event> getEvents() {
        String uri = "https://dev.itin.africa/immis/api/events?program=SXNeRfGsKcW&paging=false";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<EventList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, EventList.class);
        EventList eventList = result.getBody();
        return eventList.getEvents();
    }

    public static List<Event> getEvents(String startDate, String endDate) {
        String uri = "https://dev.itin.africa/immis/api/events?program=SXNeRfGsKcW&paging=false&&startDate=" + startDate + "&endDate=" + endDate;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<EventList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, EventList.class);
        EventList eventList = result.getBody();
        return eventList.getEvents();
    }

    public static void main(String[] args) {
        System.out.println(getEvents("2019-10-01", "2019-10-31"));

    }

}
