package com.malt.model.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Condition Operator
 *
 * @author Tanguy
 * @version 1.1
 * @since 02 June 2019
 */
@Getter
@Setter
public class ConditionOperatorDTO extends ConditionDTO {

	String ruleType;

	List<ConditionDTO> conditions = new ArrayList<>();
}
