package com.malt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * If something went wrong on internal operations
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException {

	public InternalErrorException(final String string) {
		super(string);
	}

	public InternalErrorException(final String message, final Throwable t) {
		super(message, t);
	}

	public InternalErrorException(final Exception e) {
		super(e.getMessage(), e.getCause());
	}

	private static final long serialVersionUID = -4218264112815117991L;

}
