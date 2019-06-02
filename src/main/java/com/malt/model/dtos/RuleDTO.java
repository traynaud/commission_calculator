package com.malt.model.dtos;

import com.malt.model.Rule;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Commission
 * Request<br/>
 *
 * @author Tanguy
 * @version 1.1
 * @since 02 June 2019
 */
@Getter
@Setter
public class RuleDTO {

	Long id;
	String name;
	double rate;
	ConditionDTO condition;

	public RuleDTO() {

	}

	public RuleDTO(final Rule rule) {
		id = rule.getId();
		name = rule.getName();
		rate = rule.getRate();
		if (rule.getCondition() != null) {
			condition = rule.getCondition().toDTO();
		} else {
			condition = null;
		}
	}
}
