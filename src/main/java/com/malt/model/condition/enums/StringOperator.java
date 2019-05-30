package com.malt.model.condition.enums;

/**
 * Represents operators that can be applied on String objects
 * 
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum StringOperator {
	EQUALS("eq"),
	START_WITH("sw"),
	END_WITH("ew"),
	CONTAINS("cont");

	private final String operator;

	private StringOperator(String operator) {
		this.operator = operator;
	}

	public static StringOperator fromString(String str) {
		for (StringOperator condition : StringOperator.values()) {
			if (condition.operator.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
