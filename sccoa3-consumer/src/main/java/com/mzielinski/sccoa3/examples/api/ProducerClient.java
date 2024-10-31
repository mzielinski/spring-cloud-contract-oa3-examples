package com.mzielinski.sccoa3.examples.api;

import com.mzielinski.sccoa3.examples.model.producer.Event;
import com.mzielinski.sccoa3.examples.model.producer.EventsResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class ProducerClient {

    private static final String PRODUCER_SERVER = "http://localhost:6969";
    private static final String PRODUCER_EVENTS_URL = "%s/v1/events?date=%s";

    private final RestTemplate restTemplate;

    public ProducerClient(RestTemplateBuilder restTemplate) {
        this.restTemplate = requireNonNull(restTemplate).build();
    }

    public List<Event> callProducerEndpoint(String date) {
        String url = String.format(PRODUCER_EVENTS_URL, PRODUCER_SERVER, date);
        ResponseEntity<EventsResponse> events = restTemplate.getForEntity(url, EventsResponse.class);

        if (events.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Cannot get events for URL: " + url +
                    ". Status: " + events.getStatusCode() +
                    ". Body: " + events.getBody());
        }

        return Optional.ofNullable(events.getBody())
                .map(EventsResponse::getEvents)
                .orElse(List.of());
    }
}
