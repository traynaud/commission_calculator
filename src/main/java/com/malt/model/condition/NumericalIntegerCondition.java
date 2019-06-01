package com.malt.model.condition;

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

import com.malt.model.condition.enums.NumericalOperator;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a valued condition that is applied on a Numerical Object
 *
 * @author Tanguy
 * @version 1.1
 * @since 30 May 2019
 *
 */
@Entity
@Table
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public final class NumericalIntegerCondition extends ValueCondition {

	@MapKeyEnumerated(value = EnumType.STRING)
	@ElementCollection
	@JoinTable(joinColumns = @JoinColumn(name = "conditionId"))
	@MapKeyColumn(name = "operator")
	@Column(name = "value")
	final Map<NumericalOperator, Integer> operators = new EnumMap<>(NumericalOperator.class);

	public boolean check(final Integer var) {
		for (final NumericalOperator operator : operators.keySet()) {
			final Integer constraint = operators.get(operator);
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

	@Override
	public String toString() {
		return name + ": " + operators;
	}
}
