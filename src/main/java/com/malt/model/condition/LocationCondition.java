package com.malt.model.condition;

import java.util.EnumMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.malt.model.Location;
import com.malt.model.condition.enums.LocationOperator;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a valued condition that is applied on a Location Object
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
@Entity
@Table
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class LocationCondition extends ValueCondition {

	@ElementCollection
	@MapKeyEnumerated(EnumType.STRING)
	final Map<LocationOperator, Location> operators = new EnumMap<>(LocationOperator.class);

	public boolean check(final Location var) {
		for (final LocationOperator operator : operators.keySet()) {
			final Location constraint = operators.get(operator);
			switch (operator) {
			case IN_CONTINENT:
				if (!var.getContinent().equals(constraint.getContinent())) {
					return false;
				}
				break;
			case IN_COUNTRY:
				if (!var.getCountry().equals(constraint.getCountry())) {
					return false;
				}
				break;
			default:
				break;
			}
		}
		return true;
	}
}