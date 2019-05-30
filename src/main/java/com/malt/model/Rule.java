package com.malt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.malt.model.condition.Condition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent a rule
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {
	// TODO

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@EqualsAndHashCode.Include
	Long id;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	double rate;

	@Column(nullable = true)
	Condition condition;

}
