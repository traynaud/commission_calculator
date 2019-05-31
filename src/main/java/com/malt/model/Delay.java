package com.malt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.PeriodType;

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

	private static Period normalizePeriod(final MutablePeriod period) {
		return normalizePeriod(period.toPeriod());
	}

	private static Period normalizePeriod(Period period) {
		period = period.normalizedStandard(PeriodType.yearMonthDayTime());
		while (period.getDays() > 30) {
			period = period.minusDays(30);
			period = period.plusMonths(1);
		}
		return period.normalizedStandard(PeriodType.yearMonthDayTime());
	}
}
