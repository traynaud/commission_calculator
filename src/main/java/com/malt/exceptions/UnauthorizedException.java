package com.malt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * If the user is not authorized to do the requested action
 * 
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException(final String string) {
		super(string);
	}

	private static final long serialVersionUID = 6190145084960402913L;
}