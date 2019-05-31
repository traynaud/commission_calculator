package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.AndCondition;

public interface AndConditionRepository extends JpaRepository<AndCondition, Long> {

}
