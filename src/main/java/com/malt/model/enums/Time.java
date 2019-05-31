package com.malt.model.enums;

/**
 * List the values that a period of time can be
 *
 * @author Tanguy
 * @version 1.1
 * @since 30 May 2019
 *
 */
public enum Time {
	YEAR("year", "years"),
	MONTH("month", "months"),
	DAY("day", "days"),
	HOUR("hour", "hours"),
	MINUTE("minute", "minutes"),
	SECOND("second", "seconds");

	private final String[] values;

	private Time(final String... values) {
		this.values = values;
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
