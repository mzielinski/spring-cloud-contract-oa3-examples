package com.mzielinski.sccoa3.examples;

import com.mzielinski.sccoa3.examples.api.model.EventDto;
import com.mzielinski.sccoa3.examples.api.model.EventResponseDto;
import com.mzielinski.sccoa3.examples.controller.ConferenceEventsController;
import com.mzielinski.sccoa3.examples.error.ErrorHandler;
import com.mzielinski.sccoa3.examples.service.EventsService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProducerApplication.class)
@TestPropertySource(locations = "classpath:application.yml")
public class ContractVerifierBase {

    @Autowired
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    ErrorHandler errorHandler;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders
                        .standaloneSetup(new ConferenceEventsController(new EventsServiceStub()))
                        .setControllerAdvice(errorHandler)
                        .setMessageConverters(mappingJackson2HttpMessageConverter)
        );
    }

    private static class EventsServiceStub extends EventsService {

        @Override
        public EventResponseDto findEvents(LocalDate date) {
            if (LocalDate.parse("2022-04-13").isEqual(date)) {
                return http200(date);
            } else if (LocalDate.parse("2030-04-13").isEqual(date)) {
                http500();
            }
            throw new UnsupportedOperationException(String.format("Date %s is not supported by event-service-stub", date));
        }

        private EventResponseDto http200(LocalDate date) {
            return new EventResponseDto().date(date.toString())
                    .addEventsItem(new EventDto()
                            .name("Consumer-Driven Contract Workshops")
                            .startTime(LocalDateTime.parse(date + "T11:00:00"))
                            .endTime(LocalDateTime.parse(date + "T12:00:00"))
                            .durationInMinutes(60)
                            .author("Maciej Zielinski"));
        }

        private void http500() {
            throw new RuntimeException("Some unexpected Error");
        }
    }
}
