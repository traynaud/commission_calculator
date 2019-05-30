package com.malt.model.condition;

import java.util.EnumMap;
import java.util.Map;

import com.malt.model.condition.enums.StringOperator;

import lombok.Getter;

/**
 * Represents a valued condition that is applied on a String Object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public class StringCondition extends ValueCondition {

	@Getter
	final Map<StringOperator, String> operators = new EnumMap<>(StringOperator.class);

	public boolean check(final String var) {
		for (final StringOperator operator : operators.keySet()) {
			final String constraint = operators.get(operator);
			switch (operator) {
			case CONTAINS:
				if (!var.contains(constraint)) {
					return false;
				}
				break;
			case END_WITH:
				if (!var.endsWith(constraint)) {
					return false;
				}
				break;
			case EQUALS:
				if (!var.equals(constraint)) {
					return false;
				}
				break;
			case START_WITH:
				if (!var.startsWith(constraint)) {
					return false;
				}
				break;
			default:
				break;
			}
		}
		return true;
	}
}
