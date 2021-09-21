package com.example.rsocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.rsocket.dto.ChartResponseDto;
import com.example.rsocket.dto.ComputationRequestDto;
import com.example.rsocket.dto.ComputationResponseDto;

import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Lec01RSocketTest {

	private RSocketRequester requester;
	
	@Autowired
	private RSocketRequester.Builder builder;
	
	@BeforeAll
	public void setup() {
		this.requester = this.builder
				.transport(TcpClientTransport.create("localhost",6565));
	}
	
	@Test
	public void fireAndForget() {
		Mono<Void> mono = this.requester.route("math.service.print")
						.data(new ComputationRequestDto(5))
						.send();
		
		StepVerifier.create(mono)
				.verifyComplete();
	}
	
	@Test
	public void requestResponse() {
		Mono<ComputationResponseDto> mono = this.requester.route("math.service.square")
						.data(new ComputationRequestDto(5))
						.retrieveMono(ComputationResponseDto.class)
						.doOnNext(System.out::println);
		
		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();
	}
	
	@Test
	public void requestStream() {
		Flux<ComputationResponseDto> flux = this.requester.route("math.service.table")
						.data(new ComputationRequestDto(5))
						.retrieveFlux(ComputationResponseDto.class)
						.doOnNext(System.out::println);
		
		StepVerifier.create(flux)
				.expectNextCount(10)
				.verifyComplete();
	}
	
	@Test
	public void requestChannel() {
		
	Flux<ComputationRequestDto> dtoFlux = Flux.range(-10, 21)
			.map(ComputationRequestDto::new);
		
		Flux<ChartResponseDto> flux = this.requester.route("math.service.chart")
						.data(dtoFlux)
						.retrieveFlux(ChartResponseDto.class)
						.doOnNext(System.out::println);
		
		StepVerifier.create(flux)
				.expectNextCount(21)
				.verifyComplete();
	}
}
