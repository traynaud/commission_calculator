package com.malt.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malt.model.Address;
import com.malt.model.Location;
import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;
import com.malt.repositories.AddressRepository;
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
	private final static String REGEX_IP_ADDRESS = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private AddressRepository addressRepository;

	private final Map<String, Country> cache_adresses = new HashMap<>();

	public LocalisationService() {
		// TODO: >>TMP: REMOVE WHEN IP TRACKER IS WORKING
		cache_adresses.put("217.127.206.227", Country.SPAIN);
	}

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

	/**
	 * Find the country related to the specified Address
	 *
	 * @param IPaddress (String) an IP address as String
	 * @return {@link Country}
	 */
	public Country getCountryFromAddress(final String IPaddress) {
		// TODO: check address regex
		if (cache_adresses.containsKey(IPaddress)) {
			return cache_adresses.get(IPaddress);
		}
		final Optional<Address> opt_address = addressRepository.findByAddress(IPaddress);
		if (opt_address.isPresent()) {
			final Address address = opt_address.get();
			final Country country = Country.fromString(address.getLocation());
			if (country == null) {
				logger.warn("Unable to resolve '{}' as a valid country from IP {}", address.getLocation(), IPaddress);
				return null;
			}
			cache_adresses.put(IPaddress, country);
			return country;
		}
		throw new UnsupportedOperationException("LocalisationService#getCountryFromAddress : Not implemented Yet!");
	}

	private static boolean checkIPAdress(final String address) {
		if (address == null || address.isEmpty()) {
			return false;
		}
		return address.matches(REGEX_IP_ADDRESS);
	}
}
