package org.acme.kafka;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.acme.kafka.quarkus.Movie;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Slf4j
@ApplicationScoped
public class MovieConsumer {

    private final List<Object> messageSink = Collections.synchronizedList(new ArrayList<>());

    @Incoming("movies-from-kafka")
    public void messageConsumer(ConsumerRecord<String, Movie> consumedRecord) {
        log.info("Message consumed: {}", consumedRecord);
        messageSink.add(consumedRecord.value());
    }

}