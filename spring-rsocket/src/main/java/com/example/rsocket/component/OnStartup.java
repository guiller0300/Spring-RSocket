package com.example.rsocket.component;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class OnStartup implements CommandLineRunner {

	private final Sinks.Many<String> sink;

	@Autowired
	public OnStartup(Sinks.Many<String> sink) {
		this.sink = sink;
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.interval(Duration.ofSeconds(2)).subscribe(e -> {
			this.sink.tryEmitNext("Hello from server. Current time is: " + System.currentTimeMillis());
		});
	}
}
