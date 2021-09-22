package com.example.rsocket.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Controller
public class RSocketController {
	
	private final Flux<String> stream;
	private final Sinks.Many<Integer> sink;

	@Autowired
	public RSocketController(final Flux<String> stream, final Sinks.Many<Integer> sink) {
		this.stream = stream;
		this.sink = sink;
	}

	@MessageMapping("my.time-updates.stream")
	public Flux<String> getTimeUpdatesStream() {
		return stream;
	}
	
	@MessageMapping("number.stream")
    public Flux<Integer> responseStream(Integer number) {
        return Flux.range(1, number)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(this.sink::tryEmitNext);
    }
}
