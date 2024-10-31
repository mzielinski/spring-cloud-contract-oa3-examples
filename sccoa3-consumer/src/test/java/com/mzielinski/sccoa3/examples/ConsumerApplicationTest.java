package com.mzielinski.sccoa3.examples;

import com.mzielinski.sccoa3.examples.model.NotificationResult;
import com.mzielinski.sccoa3.examples.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"com.mzielinski.sccoa3:sccoa3-producer:+:stubs:6969"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ConsumerApplicationTest {

    private static final LocalDateTime FIVE_MINUTES_BEFORE_EVENT = LocalDateTime.parse("2022-04-13T10:55:00");

    @Autowired
    private NotificationService notificationService;

    @Test
    public void shouldReturnEventsForGivenDayWithHttpStatusCode200() {
        // when
        List<NotificationResult> notifications = notificationService.findEventsWhichStartsIn(
                FIVE_MINUTES_BEFORE_EVENT, Duration.ofMinutes(10));

        // then:
        assertThat(notifications.size()).isEqualTo(1);
        assertThat(notifications.get(0).getEventName()).isEqualTo("Consumer-Driven Contract Workshops");
        assertThat(notifications.get(0).getStartsInMinutes()).isEqualTo(5);
    }
}