package com.malt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * If the controller receive bad request parameters
 * 
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQueryException extends RuntimeException {

	public InvalidQueryException(final String string) {
		super(string);
	}

	private static final long serialVersionUID = 6190145084960402919L;
}