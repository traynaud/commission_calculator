package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.LocationCondition;

public interface LocationConditionRepository extends JpaRepository<LocationCondition, Long> {

}
