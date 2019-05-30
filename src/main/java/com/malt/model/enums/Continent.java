package com.malt.model.enums;

/**
 * Exhaustive list of available continent<br/>
 * In real condition, these data is better to be loaded from config files
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
public enum Continent {
	EUROPE("europe"),
	NORTH_AMERICA("north america", "amérique du nord"),
	SOUTH_AMERICA("south america", "amérique du sud"),
	AUSTRALIA("australia", "australie"),
	ASIA("asia", "asie"),
	AFRICA("africa", "afrique");

	private final String[] names;

	private Continent(final String... names) {
		this.names = names;
	}

	public static Continent fromString(final String str) {
		for (final Continent time : Continent.values()) {
			for (final String value : time.names) {
				if (value.equalsIgnoreCase(str)) {
					return time;
				}
			}
		}
		return null;
	}
}
