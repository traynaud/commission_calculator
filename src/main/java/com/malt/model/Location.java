package com.malt.model;

import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;

import lombok.Getter;

/**
 * Describe a Location, with different levels of precision
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public class Location {

	@Getter
	Country country;
	Continent continent;

	public Continent getContinent() {
		if (continent == null) {
			return getCountry().getContinent();
		}
		return continent;
	}
}
