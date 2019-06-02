package com.malt.model.condition;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
 * Describe a list of sub-conditions that validate the condition if at least one
 * is true;
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
public final class OrCondition extends Condition {

	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Condition> conditions;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(">>OR-Start[");
		if (conditions != null && !conditions.isEmpty()) {
			for (final Condition condition : conditions) {
				sb.append("\n\t").append(condition);
			}
		} else {
			sb.append("\n\t-EMPTY-");
		}
		sb.append("\n]OR-End<<");
		return sb.toString();
	}

	@Override
	public ConditionOperatorDTO toDTO() {
		final ConditionOperatorDTO conditionDTO = new ConditionOperatorDTO();
		conditionDTO.setRuleType(Operator.OR.getOperator());
		if (conditions != null && !conditions.isEmpty()) {
			for (final Condition contition : conditions) {
				conditionDTO.getConditions().add(contition.toDTO());
			}
		}
		return conditionDTO;
	}
}
