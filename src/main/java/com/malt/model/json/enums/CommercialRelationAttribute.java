package com.malt.model.json.enums;

import static com.malt.model.enums.ParameterType.DATE_TIME;

import com.malt.model.enums.ParameterType;
import com.malt.model.json.CommercialRelation;

import lombok.Getter;

/**
 * Represents Attributes that describe a {@link CommercialRelation} object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum CommercialRelationAttribute {
	FIRST_MISSION("firstmission", DATE_TIME, true),
	LAST_MISSION("last_mission", DATE_TIME, true);

	@Getter
	private final String key;
	@Getter
	private final ParameterType type;
	@Getter
	private final boolean mandatory;

	private CommercialRelationAttribute(final String key, final ParameterType type) {
		this(key, type, false);
	}

	private CommercialRelationAttribute(final String key, final ParameterType type, final boolean mandatory) {
		this.key = key;
		this.type = type;
		this.mandatory = mandatory;
	}

	public static CommercialRelationAttribute fromString(final String str) {
		for (final CommercialRelationAttribute condition : CommercialRelationAttribute.values()) {
			if (condition.key.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
