package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.NumericalDoubleCondition;

public interface NumericalDoubleConditionRepository extends JpaRepository<NumericalDoubleCondition, Long> {

}
