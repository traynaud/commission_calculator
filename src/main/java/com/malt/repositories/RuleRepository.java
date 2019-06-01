package com.malt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.Rule;

/**
 * Repository to work with {@link Rule}s in the Database
 *
 * @author Tanguy
 * @version 1.0
 * @since 31 May 2019
 *
 */
public interface RuleRepository extends JpaRepository<Rule, Long> {

	Optional<Rule> findByName(String name);
}
