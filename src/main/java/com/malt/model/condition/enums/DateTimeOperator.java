package com.malt.model.condition.enums;

import lombok.Getter;

/**
 * Represents operators that can be applied on Date / Time objects
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum DateTimeOperator {
	BEFORE("be"),
	AT("at"),
	AFTER("af");

	@Getter
	private final String operator;

	private DateTimeOperator(final String operator) {
		this.operator = operator;
	}

	public static DateTimeOperator fromString(final String str) {
		for (final DateTimeOperator condition : DateTimeOperator.values()) {
			if (condition.operator.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
