package com.malt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Describe a Location, with different levels of precision
 *
 * @author Tanguy
 * @version 1.1
 * @since 30 May 2019
 *
 */
@Entity
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location implements Serializable {

	private static final long serialVersionUID = -2093272794747087539L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@EqualsAndHashCode.Include
	Long id;

	@Column
	@Getter
	@Enumerated(EnumType.STRING)
	Country country;

	@Enumerated(EnumType.STRING)
	Continent continent;

	@Column(name = "continent")
	public Continent getContinent() {
		if (continent == null) {
			return getCountry().getContinent();
		}
		return continent;
	}

	@Override
	public String toString() {
		return getContinent() + (country != null ? "->" + country : "");
	}
}
