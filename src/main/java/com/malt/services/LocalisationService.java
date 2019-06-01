package com.malt.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malt.model.Location;
import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;
import com.malt.repositories.LocationRepository;

/**
 * This {@link Service} manage the IP based localisation related features<br/>
 * Also manage everything related to location
 *
 * @author Tanguy
 * @version 1.1
 * @since 31 May 2019
 *
 */
@Service
public class LocalisationService {

	private static final Logger logger = LoggerFactory.getLogger(LocalisationService.class);

	@Autowired
	private LocationRepository locationRepository;

	/**
	 * Find if exists and return a {@link Location} based on a {@link Country}<br/>
	 * The location will be added if its not exists yet!
	 *
	 * @param country ({@link Country})
	 * @return {@link Location}
	 */
	@Transactional
	public Location getLocation(final Country country) {
		final Optional<Location> opt_country = locationRepository.findByCountry(country);
		if (opt_country.isPresent()) {
			return opt_country.get();
		}
		Location location = new Location();
		location.setCountry(country);
		location = locationRepository.save(location);
		return location;
	}

	/**
	 * Find if exists and return a {@link Location} based on a
	 * {@link Continent}<br/>
	 * The location will be added if its not exists yet!
	 *
	 * @param continent ({@link Continent})
	 * @return {@link Location}
	 */
	@Transactional
	public Location getLocation(final Continent continent) {
		final Optional<Location> opt_continent = locationRepository.findByContinent(continent);
		if (opt_continent.isPresent()) {
			return opt_continent.get();
		}
		Location location = new Location();
		location.setContinent(continent);
		location = locationRepository.save(location);
		return location;
	}
}
