package com.malt.model.condition.enums;

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

	private final String operator;

	private DateTimeOperator(String operator) {
		this.operator = operator;
	}

	public static DateTimeOperator fromString(String str) {
		for (DateTimeOperator condition : DateTimeOperator.values()) {
			if (condition.operator.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
