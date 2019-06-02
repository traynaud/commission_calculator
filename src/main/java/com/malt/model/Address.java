package com.malt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONException;
import org.json.JSONObject;

import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Store an IP address and its related Location<br/>
 * V2 also stores Json Data extracted from IP Stack
 *
 * @author Tanguy
 * @version 2.0
 * @since 01 June 2019
 *
 */
@Entity
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@EqualsAndHashCode.Include
	Long id;

	@Column(nullable = false, unique = true)
	String address;

	@Enumerated(EnumType.STRING)
	Country country;

	@Enumerated(EnumType.STRING)
	Continent continent;

	@Column(columnDefinition = "TEXT")
	String json;

	public Country getCountry() {
		if (country != null) {
			return country;
		}
		final String countryName = search("country_name");
		if (countryName == null) {
			return null;
		}
		return Country.fromString(countryName);
	}

	public Continent getContinent() {
		if (continent != null) {
			return continent;
		} else if (country != null) {
			return country.getContinent();
		}
		final String continentName = search("continent_name");
		if (continentName == null) {
			return null;
		}
		return Continent.fromString(continentName);
	}

	private String search(final String key) {
		if (json == null) {
			return null;
		}
		try {
			final JSONObject jsonObject = new JSONObject(json);
			return jsonObject.optString(key);
		} catch (final JSONException e) {
			return null;
		}
	}

	public static Address fromJson(final String json) {
		try {
			final Address address = new Address();
			address.setJson(json);
			final JSONObject jsonObject = new JSONObject(json);
			if (jsonObject.has("ip")) {
				address.setAddress(jsonObject.getString("ip"));
			}
			if (jsonObject.has("continent_name")) {
				address.setContinent(Continent.fromString(jsonObject.getString("continent_name")));
			}
			if (jsonObject.has("country_name")) {
				address.setCountry(Country.fromString(jsonObject.getString("country_name")));
			}
			return address;
		} catch (final JSONException e) {
			return null;
		}
	}
}
