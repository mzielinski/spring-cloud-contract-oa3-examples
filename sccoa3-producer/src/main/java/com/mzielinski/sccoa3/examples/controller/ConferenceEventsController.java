package com.mzielinski.sccoa3.examples.controller;

import com.mzielinski.sccoa3.examples.api.V1Api;
import com.mzielinski.sccoa3.examples.api.model.EventResponseDto;
import com.mzielinski.sccoa3.examples.service.EventsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ConferenceEventsController implements V1Api {

    private final EventsService eventsService;

    public ConferenceEventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @Override
    public ResponseEntity<EventResponseDto> conferenceEvents(LocalDate date) {
        return ResponseEntity.ok(eventsService.findEvents(date));
    }
}
