package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.OrCondition;

public interface OrConditionRepository extends JpaRepository<OrCondition, Long> {

}
