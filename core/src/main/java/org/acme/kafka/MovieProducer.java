package org.acme.kafka;

import io.smallrye.reactive.messaging.kafka.Record;
import org.acme.kafka.quarkus.Movie;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovieProducer {

    @Channel("movies")
    Emitter<Record<String, Movie>> movieEmitter;

    public void produceRecords(Record<String, Movie> record) {
       movieEmitter.send(record);
    }

}