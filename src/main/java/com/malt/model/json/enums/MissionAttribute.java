package com.malt.model.json.enums;

import static com.malt.model.enums.ParameterType.DELAY;

import com.malt.model.enums.ParameterType;
import com.malt.model.json.Mission;

import lombok.Getter;

/**
 * Represents Attributes that describe a {@link Mission} object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum MissionAttribute {
	LENGTH("length", DELAY, true);

	@Getter
	private final String key;
	@Getter
	private final ParameterType type;
	@Getter
	private final boolean mandatory;

	private MissionAttribute(final String key, final ParameterType type) {
		this(key, type, false);
	}

	private MissionAttribute(final String key, final ParameterType type, final boolean mandatory) {
		this.key = key;
		this.type = type;
		this.mandatory = mandatory;
	}

	public static MissionAttribute fromString(final String str) {
		for (final MissionAttribute condition : MissionAttribute.values()) {
			if (condition.key.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
