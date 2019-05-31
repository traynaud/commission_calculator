package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.StringCondition;

public interface StringConditionRepository extends JpaRepository<StringCondition, Long> {

}
