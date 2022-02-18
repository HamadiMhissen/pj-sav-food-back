package com.projetSav.PjSav.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException {
	private String message;
	private Throwable throwable;
	private HttpStatus httpStatus;
	private ZonedDateTime timestamp;

}
