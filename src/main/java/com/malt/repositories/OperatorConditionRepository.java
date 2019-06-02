package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.OperatorCondition;

public interface OperatorConditionRepository extends JpaRepository<OperatorCondition, Long> {

}
