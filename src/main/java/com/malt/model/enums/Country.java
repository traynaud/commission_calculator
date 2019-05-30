package com.malt.model.enums;

import static com.malt.model.enums.Continent.ASIA;
import static com.malt.model.enums.Continent.EUROPE;
import static com.malt.model.enums.Continent.NORTH_AMERICA;

import lombok.Getter;

/**
 * Exhaustive list of available countries<br/>
 * In real condition, these data is better to be loaded from config files
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
public enum Country {
	FRANCE(EUROPE, "fr", "france"),
	SPAIN(EUROPE, "es", "spain", "espagne"),
	ITALY(EUROPE, "it", "italy", "italie"),
	UNITEK_KINGDOM(EUROPE, "gb", "united kingdom", "great Britain", "royaume uni", "grande bretagne"),
	GERMANY(EUROPE, "de", "germany", "allemagne"),
	PORTUGAL(EUROPE, "pt", "portugal"),
	SWITZERLAND(EUROPE, "ch", "switzerland", "swiss confederation", "suisse"),
	CANADA(NORTH_AMERICA, "ca", "canada"),
	JAPAN(ASIA, "jp", "japan", "japon");

	@Getter
	private final String[] names;
	@Getter
	private final String shortName;
	@Getter
	private final Continent continent;

	private Country(final Continent continent, final String shortName, final String... names) {
		this.shortName = shortName;
		this.names = names;
		this.continent = continent;
	}

	public static Country fromString(final String str) {
		for (final Country time : Country.values()) {
			if (str.equalsIgnoreCase(time.shortName)) {
				return time;
			}
			for (final String value : time.names) {
				if (value.equalsIgnoreCase(str)) {
					return time;
				}
			}
		}
		return null;
	}
}
