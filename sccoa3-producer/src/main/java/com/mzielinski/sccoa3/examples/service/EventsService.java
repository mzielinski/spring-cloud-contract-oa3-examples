package com.mzielinski.sccoa3.examples.service;

import com.mzielinski.sccoa3.examples.api.model.EventResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventsService {

    public EventResponseDto findEvents(LocalDate date) {
        throw new UnsupportedOperationException("It is not implemented yet");
    }
}
