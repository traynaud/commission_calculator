package com.malt.model.condition.enums;

import lombok.Getter;

/**
 * List operators that can be used to describe a complex condition
 *
 * @author Tanguy
 * @version 1.0
 * @since 31 May 2019
 *
 */
public enum Operator {
	AND("and"),
	OR("or"),
	NON("no"),
	NAND("nand"),
	NOR("nor"),
	XOR("xor");

	@Getter
	private final String operator;

	private Operator(final String operator) {
		this.operator = operator;
	}

	public static Operator fromString(String str) {
		str = str.trim();
		if (str.startsWith("@")) {
			str = str.substring(1);
		}
		for (final Operator operator : Operator.values()) {
			if (operator.operator.equalsIgnoreCase(str)) {
				return operator;
			}
		}
		return null;
	}

}
