package com.mzielinski.sccoa3.examples.model.producer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonIgnoreProperties
public class EventsResponse {

    @NotNull
    private final String date;
    private final List<Event> events;

    @JsonCreator
    public EventsResponse(String date, List<Event> events) {
        this.date = date;
        this.events = events;
    }

    public String getDate() {
        return date;
    }

    public List<Event> getEvents() {
        return events;
    }
}