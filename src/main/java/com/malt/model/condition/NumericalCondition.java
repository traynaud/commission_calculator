package com.malt.model.condition;

import java.util.EnumMap;
import java.util.Map;

import com.malt.model.condition.enums.NumericalOperator;

import lombok.Getter;

/**
 * Represents a valued condition that is applied on a Numerical Object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public class NumericalCondition<T extends Comparable<T>> extends ValueCondition {

	@Getter
	final Map<NumericalOperator, T> operators = new EnumMap<>(NumericalOperator.class);

	public boolean check(final T var) {
		for (final NumericalOperator operator : operators.keySet()) {
			final T constraint = operators.get(operator);
			final int compare = constraint.compareTo(var);
			switch (operator) {
			case EQUALS:
				if (compare != 0) {
					return false;
				}
				break;
			case GREATER_THAN:
				if (compare > 0) {
					return false;
				}
				break;
			case LOWER_THAN:
				if (compare < 0) {
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
