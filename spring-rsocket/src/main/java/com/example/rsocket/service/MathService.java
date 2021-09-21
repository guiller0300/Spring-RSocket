package com.example.rsocket.service;

import org.springframework.stereotype.Service;

import com.example.rsocket.dto.ChartResponseDto;
import com.example.rsocket.dto.ComputationRequestDto;
import com.example.rsocket.dto.ComputationResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathService {
	
	public Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono){
		return requestDtoMono
					.doOnNext(System.out::println)
					.then();
		}
	
	//rr
	public Mono<ComputationResponseDto> findSquare(Mono<ComputationRequestDto> requestDtoMono){
		return requestDtoMono
				.map(ComputationRequestDto::getInput)
				.map(i -> new ComputationResponseDto(i, i * i));
	}
	
	//rs 
	public Flux<ComputationResponseDto> tableStream(ComputationRequestDto dto){
		return Flux.range(1, 10)
					.map(i -> new ComputationResponseDto(dto.getInput(), dto.getInput() * i));
	}
	
	//rc
	public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> requestDtoFlux){
		return requestDtoFlux
					.map(ComputationRequestDto::getInput)
					.map(i -> new ChartResponseDto(i, (i * i) + 1));
	}
	
}
