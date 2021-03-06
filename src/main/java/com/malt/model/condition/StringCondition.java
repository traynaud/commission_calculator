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

import com.malt.model.condition.enums.StringOperator;
import com.malt.model.dtos.ConditionValueDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a valued condition that is applied on a String Object
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
public final class StringCondition extends ValueCondition {

	@MapKeyEnumerated(value = EnumType.STRING)
	@ElementCollection
	@JoinTable(joinColumns = @JoinColumn(name = "conditionId"))
	@MapKeyColumn(name = "operator")
	@Column(name = "value")
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

	@Override
	public String toString() {
		return name + ": " + operators;
	}

	@Override
	public ConditionValueDTO toDTO() {
		final ConditionValueDTO conditionDTO = new ConditionValueDTO();
		conditionDTO.setName(name.getIdentifier());
		for (final StringOperator operator : operators.keySet()) {
			conditionDTO.getParameters().put(operator.getOperator(), operators.get(operator));
		}
		return conditionDTO;
	}
}
