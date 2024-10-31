package com.mzielinski.sccoa3.examples.model;

public class NotificationResult {

    private final String eventName;
    private final long startsInMinutes;

    public NotificationResult(String eventName, long startsInMinutes) {
        this.eventName = eventName;
        this.startsInMinutes = startsInMinutes;
    }

    public String getEventName() {
        return eventName;
    }

    public long getStartsInMinutes() {
        return startsInMinutes;
    }
}
