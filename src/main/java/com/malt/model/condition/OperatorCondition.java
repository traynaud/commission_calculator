package com.malt.model.condition;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.malt.model.condition.enums.Operator;
import com.malt.model.dtos.ConditionOperatorDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a terminal named condition, associated to a value
 *
 * @author Tanguy
 * @version 1.0
 * @since 02 June 2019
 *
 */
@Entity
@Table
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class OperatorCondition extends Condition {

	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Condition> conditions;

	@Enumerated(EnumType.STRING)
	Operator operator;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(">>" + operator.name() + "-Start[");
		if (conditions != null && !conditions.isEmpty()) {
			for (final Condition condition : conditions) {
				sb.append("\n\t").append(condition);
			}
		} else {
			sb.append("\n\t-EMPTY-");
		}
		sb.append("\n]" + operator.name() + "-End<<");
		return sb.toString();
	}

	@Override
	public ConditionOperatorDTO toDTO() {
		final ConditionOperatorDTO conditionDTO = new ConditionOperatorDTO();
		conditionDTO.setRuleType(operator.getOperator());
		if (conditions != null && !conditions.isEmpty()) {
			for (final Condition contition : conditions) {
				conditionDTO.getConditions().add(contition.toDTO());
			}
		}
		return conditionDTO;
	}
}
