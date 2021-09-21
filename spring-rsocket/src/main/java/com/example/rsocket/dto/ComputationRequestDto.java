package com.example.rsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComputationRequestDto {
	
	private int input;
	
	
	
	public ComputationRequestDto() {
		
	}

	public ComputationRequestDto(int input) {
		super();
		this.input = input;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	@Override
	public String toString() {
		return "ComputationRequestDto [input=" + input + "]";
	}
	
}
