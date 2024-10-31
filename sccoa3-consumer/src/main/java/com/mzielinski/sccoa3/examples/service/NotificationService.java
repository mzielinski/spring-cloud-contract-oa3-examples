package com.mzielinski.sccoa3.examples.service;

import com.mzielinski.sccoa3.examples.api.ProducerClient;
import com.mzielinski.sccoa3.examples.model.NotificationResult;
import com.mzielinski.sccoa3.examples.model.producer.Event;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.Objects.requireNonNull;

@Service
public class NotificationService {

    private final ProducerClient producerClient;

    public NotificationService(ProducerClient producerClient) {
        this.producerClient = producerClient;
    }

    public List<NotificationResult> findEventsWhichStartsIn(LocalDateTime currentDateTime, Duration beforeEvent) {
        requireNonNull(beforeEvent, "before event cannot be null");
        requireNonNull(currentDateTime, "current date cannot be null");

        return producerClient.callProducerEndpoint(currentDateTime.format(ISO_DATE)).stream()
                .filter(event -> validForNotification(currentDateTime, beforeEvent, event))
                .map(event -> new NotificationResult(
                        event.getName(), calculateStartTimeInMinutes(currentDateTime, event.getStartTime())))
                .collect(Collectors.toList());
    }

    private boolean validForNotification(LocalDateTime currentDateTime, Duration duration, Event event) {
        return event.getStartTime().isAfter(currentDateTime.minusMinutes(duration.toMinutes()));
    }

    private long calculateStartTimeInMinutes(LocalDateTime currentDateTime, LocalDateTime eventStartTime) {
        return (eventStartTime.toEpochSecond(UTC) - currentDateTime.toEpochSecond(UTC)) / 60;
    }
}
