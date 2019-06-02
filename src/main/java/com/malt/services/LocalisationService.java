package com.malt.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malt.exceptions.InternalErrorException;
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
 * @version 1.2
 * @since 31 May 2019
 *
 */
@Service
public class LocalisationService {

	private static final Logger logger = LoggerFactory.getLogger(LocalisationService.class);
	private final static String REGEX_IP_ADDRESS = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

	private final static String URL_API_IPSTACK = "http://api.ipstack.com/";

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Value("${api_ipstack_token}")
	private String apiIpStackToken;

	private final Map<String, Country> cache_adresses = new HashMap<>();

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
	 * @param ipAddress (String) an IP address as String
	 * @return {@link Country}
	 */
	@Transactional
	public Country getCountryFromAddress(final String ipAddress) {
		if (!checkIPAdress(ipAddress)) {
			logger.warn("Incorrect Ip Address '{}' will not be processed!", ipAddress);
			return null;
		}
		if (cache_adresses.containsKey(ipAddress)) {
			logger.info("Cache : Get address '{}'->{} from cache 1 (RAM)", ipAddress, cache_adresses.get(ipAddress));
			return cache_adresses.get(ipAddress);
		}
		final Optional<Address> opt_address = addressRepository.findByAddress(ipAddress);
		if (opt_address.isPresent()) {
			final Address address = opt_address.get();
			final Country country = address.getCountry();
			if (country == null) {
				return null;
			}
			logger.info("Cache : Get address '{}'->{} from cache 2 (DB)", ipAddress, country);
			cache_adresses.put(ipAddress, country);
			return country;
		}
		final String result;
		try {
			result = callIpStackAPI(ipAddress);
		} catch (final IOException e) {
			logger.error("An error occured during IPStack call: {}: {}", e.getClass().getSimpleName(), e.getMessage());
			throw new InternalErrorException(e);
		}
		Address address = Address.fromJson(result);
		if (address == null) {
			logger.warn("Unable to resolve address with IP STACK : {}", ipAddress);
			return null;
		}
		if (address.getAddress() == null) {
			address.setAddress(ipAddress);
		}
		address = addressRepository.save(address);
		final Country country = address.getCountry();
		if (country == null) {
			logger.warn("Unable to resolve address with IP STACK : {}", ipAddress);
			return null;
		}
		logger.info("No cache : Get address '{}'->{} from IPStack (API)", ipAddress, country);
		cache_adresses.put(ipAddress, country);
		return country;
	}

	/**
	 * Call Ip stack API and get data about
	 *
	 * @param ip
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String callIpStackAPI(final String ip) throws ClientProtocolException, IOException {
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet request = new HttpGet(URL_API_IPSTACK + ip + "?access_key=" + apiIpStackToken);
		final HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			final StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} else {
			logger.error("An error occured while calling IP Stack {} : Code {}", URL_API_IPSTACK + ip,
					response.getStatusLine().getStatusCode());
			return null;
		}
	}

	/**
	 * Use Regex to check if an IP Address is valid
	 *
	 * @param address An IP address
	 * @return <code>true</code> if the specified address is valid,
	 *         <code>false</code> otherwise
	 */
	private static boolean checkIPAdress(final String address) {
		if (address == null || address.isEmpty()) {
			return false;
		}
		return address.matches(REGEX_IP_ADDRESS);
	}
}
