package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long> {

}
