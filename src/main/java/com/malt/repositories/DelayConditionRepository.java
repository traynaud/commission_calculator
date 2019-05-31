package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.DelayCondition;

public interface DelayConditionRepository extends JpaRepository<DelayCondition, Long> {

}
