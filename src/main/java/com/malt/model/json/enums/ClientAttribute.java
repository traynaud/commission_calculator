package com.malt.model.json.enums;

import static com.malt.model.enums.ParameterType.IP_ADDRESS;

import com.malt.model.enums.ParameterType;
import com.malt.model.json.Client;

import lombok.Getter;

/**
 * Represents Attributes that describe a {@link Client} object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum ClientAttribute {
	IP("ip", IP_ADDRESS, true);

	@Getter
	private final String key;
	@Getter
	private final ParameterType type;
	@Getter
	private final boolean mandatory;

	private ClientAttribute(final String key, final ParameterType type) {
		this(key, type, false);
	}

	private ClientAttribute(final String key, final ParameterType type, final boolean mandatory) {
		this.key = key;
		this.type = type;
		this.mandatory = mandatory;
	}

	public static ClientAttribute fromString(final String str) {
		for (final ClientAttribute condition : ClientAttribute.values()) {
			if (condition.key.equalsIgnoreCase(str)) {
				return condition;
			}
		}
		return null;
	}
}
