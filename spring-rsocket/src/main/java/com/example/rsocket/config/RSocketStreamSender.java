package com.example.rsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class RSocketStreamSender {
	
	@Bean
	public Sinks.Many<String> sink(){
		return Sinks.many().replay().limit(0);
	}
	
	@Bean
	public Flux<String> streamFlux(Sinks.Many<String> sink){
		return sink.asFlux();
	}
	
	@Bean
	public Sinks.Many<Integer> sinkInt(){
		return Sinks.many().replay().limit(0);
	}
	
	@Bean
	public Flux<Integer> streamInt(Sinks.Many<Integer> sink){
		return sink.asFlux();
	}
}
