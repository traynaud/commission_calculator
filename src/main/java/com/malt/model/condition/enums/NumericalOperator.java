package com.malt.model.condition.enums;

import lombok.Getter;

/**
 * Represents operators that can be applied on Numerical objects
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum NumericalOperator {
	EQUALS("eq"),
	GREATER_THAN("gt"),
	LOWER_THAN("lt");

	@Getter
	private final String operator;

	private NumericalOperator(final String operator) {
		this.operator = operator;
	}

	public static NumericalOperator fromString(final String str) {
		for (final NumericalOperator condition : NumericalOperator.values()) {
			if (condition.operator.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
