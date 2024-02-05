package org.acme.kafka;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.kafka.Record;
import org.acme.kafka.quarkus.Movie;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@QuarkusTest
public class KafkaProcessingTest {

    @Inject
    MovieConsumer movieConsumer;

    @Inject
    MovieProducer producer;

    @Test
    void processingTest() {

        Movie movie = Movie.newBuilder()
                .setYear(2012)
                .setTitle("Star Wars")
                .setRelease(Instant.now())
                .build();

        Record<String, Movie> movieRecord = Record.of("1", movie);

        producer.produceRecords(movieRecord);

        awaitUntilSuccessfullyAssert(() -> assertThat(movieConsumer.getMessageSink()).isNotEmpty().hasSize(1));

    }

    private void awaitUntilSuccessfullyAssert(ThrowingRunnable runnable) {
        await().alias("Awaiting: producing and processing messages")
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(200))
                .pollInSameThread()
                .untilAsserted(runnable);
    }

}

