package com.malt.model.condition;

import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.Map;

import com.malt.model.condition.enums.DateTimeOperator;

import lombok.Getter;

/**
 * Represents a valued condition that is applied on a Date/Time Object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public class DateTimeCondition extends ValueCondition {

	@Getter
	final Map<DateTimeOperator, OffsetDateTime> operators = new EnumMap<>(DateTimeOperator.class);

	public boolean check(final OffsetDateTime var) {
		for (final DateTimeOperator operator : operators.keySet()) {
			final OffsetDateTime constraint = operators.get(operator);
			final int compare = constraint.compareTo(var);
			switch (operator) {
			case AT:
				if (compare != 0) {
					return false;
				}
				break;
			case BEFORE:
				if (compare < 0) {
					return false;
				}
				break;
			case AFTER:
				if (compare > 0) {
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
