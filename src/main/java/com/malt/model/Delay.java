package com.malt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.malt.model.enums.Time;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent a period of time
 *
 * @author Tanguy
 * @version 2.0
 * @since 30 May 2019
 *
 */
@Entity
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Delay implements Comparable<Delay> {

	private static final Logger logger = LoggerFactory.getLogger(Delay.class);
	private static final String REGEX_DELAY = "(\\d+[A-Za-z]+\\s*)+";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@EqualsAndHashCode.Include
	Long id;

	int seconds = 0;
	int minutes = 0;
	int hours = 0;
	int days = 0;
	int months = 0;
	int years = 0;

	/**
	 * Add a specified amount of {@link Time} into the current {@link Delay}
	 *
	 * @param time  ({@link Time}) the unit of the value
	 * @param value the amount of {@link Time} to add
	 */
	public void addTime(final Time time, final int value) {
		switch (time) {
		case YEAR:
			years += value;
			break;
		case MONTH:
			months += value;
			break;
		case DAY:
			days += value;
			break;
		case HOUR:
			hours += value;
			break;
		case MINUTE:
			minutes += value;
			break;
		case SECOND:
			seconds += value;
			break;
		}
	}

	/**
	 * Convert the current object into a {@link Period}
	 *
	 * @return ({@link Period})
	 */
	public Period toPeriod() {
		return new Period(years, months, 0, days, hours, minutes, seconds, 0);
	}

	@Override
	public int compareTo(final Delay o) {
		final Period norm = normalizePeriod(toPeriod());
		final Period o_norm = normalizePeriod(o.toPeriod());
		int compare = Integer.compare(norm.getYears(), o_norm.getYears());
		if (compare != 0) {
			return compare;
		}
		compare = Integer.compare(norm.getMonths(), o_norm.getMonths());
		if (compare != 0) {
			return compare;
		}
		compare = Integer.compare(norm.getDays(), o_norm.getDays());
		if (compare != 0) {
			return compare;
		}
		compare = Integer.compare(norm.getHours(), o_norm.getHours());
		if (compare != 0) {
			return compare;
		}
		compare = Integer.compare(norm.getMinutes(), o_norm.getMinutes());
		if (compare != 0) {
			return compare;
		}
		return Integer.compare(norm.getSeconds(), o_norm.getSeconds());
	}

	public static Delay fromString(final String str) {
		if (!str.matches(REGEX_DELAY)) {
			logger.warn("'{}' is not a valid Delay : expected '{}'", str, REGEX_DELAY);
			return null;
		}
		throw new UnsupportedOperationException("Delay#fromString : Not implemented Yet!");
	}

	/**
	 * Normalize a period to facilitate its comparison with another one
	 *
	 * @param period ({@link MutablePeriod})
	 * @return ({@link Period})
	 * @see Period#normalizedStandard()
	 */
	private static Period normalizePeriod(final MutablePeriod period) {
		return normalizePeriod(period.toPeriod());
	}

	/**
	 * Normalize a period to facilitate its comparison with another one
	 *
	 * @param period ({@link Period})
	 * @return ({@link Period})
	 * @see Period#normalizedStandard()
	 */
	private static Period normalizePeriod(Period period) {
		period = period.normalizedStandard(PeriodType.yearMonthDayTime());
		while (period.getDays() > 30) {
			period = period.minusDays(30);
			period = period.plusMonths(1);
		}
		return period.normalizedStandard(PeriodType.yearMonthDayTime());
	}
}
