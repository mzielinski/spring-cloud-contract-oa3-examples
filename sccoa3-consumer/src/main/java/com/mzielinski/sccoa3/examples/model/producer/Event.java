package com.mzielinski.sccoa3.examples.model.producer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonIgnoreProperties
public class Event {

    @NotNull
    private final String name;

    @NotNull
    private final LocalDateTime startTime;

    @NotNull
    private final int durationInMinutes;

    @JsonCreator
    public Event(String name, LocalDateTime startTime, int durationInMinutes) {
        this.name = name;
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }
}
