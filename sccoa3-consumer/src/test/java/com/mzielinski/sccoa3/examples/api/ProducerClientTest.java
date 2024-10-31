package com.mzielinski.sccoa3.examples.api;

import com.mzielinski.sccoa3.examples.model.producer.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"com.mzielinski.sccoa3:sccoa3-producer:+:stubs:6969"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ProducerClientTest {

    private static final String HTTP_200 = "2022-04-13";
    private static final String HTTP_400 = "invalid-date";
    private static final String HTTP_500 = "2030-04-13";

    @Autowired
    private ProducerClient producerClient;

    @Test
    public void shouldReturnEventsForGivenDayWithHttpStatusCode200() {
        // when
        List<Event> events = producerClient.callProducerEndpoint(HTTP_200);

        // then:
        assertThat(events.size()).isEqualTo(1);
        assertThat(events.get(0).getName()).isEqualTo("Consumer-Driven Contract Workshops");
        assertThat(events.get(0).getStartTime()).isEqualTo(LocalDateTime.parse(HTTP_200 + "T11:00:00"));
        assertThat(events.get(0).getDurationInMinutes()).isEqualTo(60);
    }

    @Test
    public void shouldReturnHttpStatusCode400WhenDateHasInvalidValue() {
        // when
        HttpClientErrorException thrown = Assertions.assertThrows(HttpClientErrorException.class,
                () -> producerClient.callProducerEndpoint(HTTP_400));

        // then:
        assertThat(thrown.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(thrown.getResponseBodyAsString()).isEqualTo("{\"message\":\"Invalid Request\"}");
    }

    @Test
    public void shouldReturnHttpStatusCode500WhenServerHasUnexpectedProblems() {
        // when
        HttpServerErrorException thrown = Assertions.assertThrows(HttpServerErrorException.class,
                () -> producerClient.callProducerEndpoint(HTTP_500));

        // then:
        assertThat(thrown.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(thrown.getResponseBodyAsString()).isEqualTo("{\"message\":\"Unexpected Error\"}");
    }
}