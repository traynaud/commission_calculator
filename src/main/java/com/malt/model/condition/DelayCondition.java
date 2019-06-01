package com.malt.model.condition;

import java.util.EnumMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.malt.model.Delay;
import com.malt.model.condition.enums.NumericalOperator;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a valued condition that is applied on a Delay Object
 *
 * @author Tanguy
 * @version 1.0
 * @since 31 May 2019
 *
 */
@Entity
@Table
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public final class DelayCondition extends ValueCondition {

	@MapKeyEnumerated(value = EnumType.STRING)
	@ManyToMany(targetEntity = Delay.class)
	@JoinTable(joinColumns = @JoinColumn(name = "conditionId"), inverseJoinColumns = @JoinColumn(name = "delayId"))
	@MapKeyColumn(name = "operator")
	final Map<NumericalOperator, Delay> operators = new EnumMap<>(NumericalOperator.class);

	public boolean check(final Delay var) {
		for (final NumericalOperator operator : operators.keySet()) {
			final Delay constraint = operators.get(operator);
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
