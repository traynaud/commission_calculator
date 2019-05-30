package com.malt.model;

import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.malt.model.enums.Time;

import lombok.Getter;

/**
 * Represent a period of time
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public class Delay implements Comparable<Delay> {

	@Getter
	private MutablePeriod period;

	public void addTime(final Time time, final int value) {
		period.add(time.getType(), value);
	}

	@Override
	public int compareTo(final Delay o) {
		final Period norm = normalizePeriod(period);
		final Period o_norm = normalizePeriod(o.period);
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
