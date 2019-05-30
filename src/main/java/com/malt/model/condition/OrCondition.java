package com.malt.model.condition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Describe a list of sub-conditions that validate the condition if at least one
 * is true;
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public class OrCondition extends Condition {

	@Getter
	List<Condition> conditions = new ArrayList<>();

}
