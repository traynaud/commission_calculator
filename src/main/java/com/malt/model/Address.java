package com.malt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Store an IP address and its related Location
 *
 * @author Tanguy
 * @version 2.0
 * @since 01 June 2019
 *
 */
@Entity
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@EqualsAndHashCode.Include
	Long id;

	@Column(nullable = false, unique = true)
	String address;

	@Column(nullable = false)
	String location;
}
