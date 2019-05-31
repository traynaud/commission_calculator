package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
