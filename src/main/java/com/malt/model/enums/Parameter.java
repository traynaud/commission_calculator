package com.malt.model.enums;

import static com.malt.model.enums.ParameterType.DELAY;
import static com.malt.model.enums.ParameterType.LOCATION;

import lombok.Getter;

/**
 * Possible values and types for parameters
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
public enum Parameter {

	MISSION_DURATION("mission.duration", DELAY),
	COMMERCIAL_RELATION_DURATION("commercialrelation.duration", DELAY),
	CLIENT_LOCATION("client.location", LOCATION),
	FREELANCER_LOCATION("freelancer.location", LOCATION);

	@Getter
	private final String identifier;
	@Getter
	private final ParameterType type;

	private Parameter(final String identifier, final ParameterType type) {
		this.identifier = identifier;
		this.type = type;
	}

	public static Parameter fromString(String str) {
		str = str.trim();
		if (str.startsWith("@")) {
			str = str.substring(1);
		}
		for (final Parameter parameter : Parameter.values()) {
			if (parameter.identifier.equalsIgnoreCase(str)) {
				return parameter;
			}
		}
		return null;
	}
}
