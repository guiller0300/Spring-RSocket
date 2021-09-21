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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec02DestinationVariableTest {
	
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
		Mono<Void> mono = this.requester.route("math.service.print.55")
						.send();
		
		StepVerifier.create(mono)
				.verifyComplete();
	}
	
}
