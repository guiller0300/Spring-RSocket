package com.example.rsocket.dto.error;

public enum StatusCode {
	
	EC001 ("given number is not within range"),
	EC002 ("your usage limir exceeded");
	
	private final String description;
	
	StatusCode(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
