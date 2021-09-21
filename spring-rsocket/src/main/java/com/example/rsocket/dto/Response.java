package com.example.rsocket.dto;

import java.util.Objects;

import com.example.rsocket.dto.error.ErrorEvent;

public class Response<T> {
	
	ErrorEvent errorResponse;
	T successResponse;
	
	public Response() {
		
	}
	
	public Response(T successResponse) {
		this.successResponse = successResponse;
	}
	
	public Response(ErrorEvent errorResponse) {
		this.errorResponse = errorResponse;
	}
	
	public boolean hasError() {
		return Objects.nonNull(this.errorResponse);
	}
	
	public ErrorEvent getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorEvent errorResponse) {
		this.errorResponse = errorResponse;
	}

	public T getSuccessResponse() {
		return successResponse;
	}

	public void setSuccessResponse(T successResponse) {
		this.successResponse = successResponse;
	}
	
	public static<T> Response<T> with(T t){
		return new Response<T>(t);
	}
	
	public static<T> Response<T> with(ErrorEvent errorResponse){
		return new Response<T>(errorResponse);
	}
	
	
	
}
