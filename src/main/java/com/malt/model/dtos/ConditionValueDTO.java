package com.malt.model.dtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Condition Value
 *
 * @author Tanguy
 * @version 1.1
 * @since 02 June 2019
 */
@Getter
@Setter
public class ConditionValueDTO extends ConditionDTO {

	String name;

	Map<String, String> parameters = new HashMap<>();
}
