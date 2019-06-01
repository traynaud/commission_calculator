package com.malt.model.condition;

import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.malt.model.condition.enums.DateTimeOperator;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a valued condition that is applied on a Date/Time Object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
@Entity
@Table
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public final class DateTimeCondition extends ValueCondition {

	@MapKeyEnumerated(EnumType.STRING)
	@ElementCollection
	@JoinTable(joinColumns = @JoinColumn(name = "conditionId"))
	@MapKeyColumn(name = "operator")
	@Column(name = "dateTime")
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

	@Override
	public String toString() {
		return name + ": " + operators;
	}
}
