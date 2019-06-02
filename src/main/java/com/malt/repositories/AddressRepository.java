package com.malt.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.Address;
import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;

/**
 * Repository to work with {@link Address}es in the Database
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 * @see Address
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findByAddress(String address);

	List<Address> findAllByCountry(Country country);

	List<Address> findAllByContinent(Continent continent);
}
