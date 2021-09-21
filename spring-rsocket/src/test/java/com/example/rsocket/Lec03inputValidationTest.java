package com.example.rsocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.rsocket.dto.Response;

import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec03inputValidationTest {
	

	private RSocketRequester requester;
	
	@Autowired
	private RSocketRequester.Builder builder;
	
	@BeforeAll
	public void setup() {
		this.requester = this.builder
				.transport(TcpClientTransport.create("localhost",6565));
	}
	
	@Test
	public void validationtest() {
		Mono<Integer> mono = this.requester.route("math.validation.double.31")
						.retrieveMono(Integer.class)
						.onErrorReturn(Integer.MIN_VALUE)
						.doOnNext(System.out::println);
		
		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();
	}
	
	@Test
	public void responseTest() {
		Mono<Response<Integer>> mono = this.requester.route("math.validation.double.response.31")
						.retrieveMono(new ParameterizedTypeReference<Response<Integer>>(){
						})
						.doOnNext(r -> {
							if(r.hasError())
								System.out.println(r.getErrorResponse().getStatusCode().getDescription());
							else
								System.out.println(r.getSuccessResponse());
						});
		
		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();
	}
}
