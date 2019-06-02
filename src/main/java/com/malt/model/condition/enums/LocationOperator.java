package com.malt.model.condition.enums;

import lombok.Getter;

/**
 * Represents operators that can be applied on Location objects
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum LocationOperator {
	IN_CONTINENT("continent"),
	IN_COUNTRY("country"),
	IN_REGION("region");

	@Getter
	private final String operator;

	private LocationOperator(final String operator) {
		this.operator = operator;
	}

	public static LocationOperator fromString(final String str) {
		for (final LocationOperator condition : LocationOperator.values()) {
			if (condition.operator.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
