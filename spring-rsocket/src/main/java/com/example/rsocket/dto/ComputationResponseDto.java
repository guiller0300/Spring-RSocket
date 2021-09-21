package com.example.rsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComputationResponseDto {
	
	private int input;
	private int output;
	
	public ComputationResponseDto() {
		
	}
	
	public ComputationResponseDto(int input, int output) {
		super();
		this.input = input;
		this.output = output;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public int getOutput() {
		return output;
	}

	public void setOutput(int output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "ComputationResponseDto [input=" + input + ", output=" + output + "]";
	}
	
	
}
