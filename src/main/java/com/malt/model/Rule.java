package com.malt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class Rule implements Comparable<Rule> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@EqualsAndHashCode.Include
	Long id;

	@Column(nullable = false, unique = true)
	String name;

	@Column(nullable = false)
	double rate;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(optional = false)
	Condition condition;

	@Override
	public String toString() {
		//@formatter:off
		return "--RULE--"
				+ "\n>id=" + id
				+ "\n>name=" + name
				+ "\n>rate=" + rate
				+ "\n>condition=" + condition;
		//@formatter:on
	}

	@Override
	public int compareTo(final Rule o) {
		return Double.compare(rate, o.rate);
	}
}
