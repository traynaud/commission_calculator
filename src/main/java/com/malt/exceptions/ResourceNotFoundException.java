package com.malt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * If a resource does not exist or is unreachable
 * 
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 5699547725406311036L;

	public ResourceNotFoundException(final String message) {
		super(message);
	}

	public ResourceNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}