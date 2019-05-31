package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.condition.DateTimeCondition;

public interface DateTimeConditionRepository extends JpaRepository<DateTimeCondition, Long> {

}
