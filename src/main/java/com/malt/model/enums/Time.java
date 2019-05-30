package com.malt.model.enums;

import static org.joda.time.DurationFieldType.days;
import static org.joda.time.DurationFieldType.hours;
import static org.joda.time.DurationFieldType.minutes;
import static org.joda.time.DurationFieldType.months;
import static org.joda.time.DurationFieldType.seconds;
import static org.joda.time.DurationFieldType.years;

import org.joda.time.DurationFieldType;

import lombok.Getter;

/**
 * List the values that a period of time can be
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
public enum Time {
	YEAR(years(), "year", "years"),
	MONTH(months(), "month", "months"),
	DAY(days(), "day", "days"),
	HOUR(hours(), "hour", "hours"),
	MINUTE(minutes(), "minute", "minutes"),
	SECOND(seconds(), "second", "seconds");

	private final String[] values;
	@Getter
	private final DurationFieldType type;

	private Time(final DurationFieldType type, final String... values) {
		this.values = values;
		this.type = type;
	}

	public static Time fromString(final String str) {
		for (final Time time : Time.values()) {
			for (final String value : time.values) {
				if (value.equalsIgnoreCase(str)) {
					return time;
				}
			}
		}
		return null;
	}
}
