package com.example.rsocket.dto.error;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEvent {
	
	private StatusCode statusCode;
	private final LocalDate date = LocalDate.now();
	
	public ErrorEvent() {
		
	}
	
	public ErrorEvent(StatusCode statusCode) {
		super();
		this.statusCode = statusCode;
	}
	
	public StatusCode getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	public LocalDate getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "ErrorEvent [statusCode=" + statusCode + ", date=" + date + "]";
	}
	
}
