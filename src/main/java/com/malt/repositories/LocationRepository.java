package com.malt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.malt.model.Location;
import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;

public interface LocationRepository extends JpaRepository<Location, Long> {

	// @Query("SELECT l FROM Location l WHERE l.country = :country")
	Optional<Location> findByCountry(Country country);

	@Query("SELECT l FROM Location l WHERE l.continent=:continent AND l.country IS NULL")
	Optional<Location> findByContinent(Continent continent);
}
