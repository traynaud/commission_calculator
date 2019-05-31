package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.NumericalIntegerCondition;

public interface NumericalIntegerConditionRepository extends JpaRepository<NumericalIntegerCondition, Long> {

}
