package com.malt.model.condition;

import com.malt.model.enums.Parameter;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a terminal named condition, associated to a value
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public abstract class ValueCondition extends Condition {

	@Setter
	@Getter
	protected Parameter name;
}
