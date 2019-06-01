package com.malt.utils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utils methods related to time
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
public class TimeUtils {

	private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS'Z'";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
			.withZone(ZoneId.of("Europe/Berlin"));;

	public static OffsetDateTime parseDateTimeFromString(final String str) {
		try {
			final ZonedDateTime dateTime = ZonedDateTime.parse(str, DATE_TIME_FORMATTER);
			return dateTime.toOffsetDateTime();
		} catch (final DateTimeParseException e) {
			logger.warn("unable to parse string '{}' as date:\n\t>{}: {}", str, e.getClass().getName(), e.getMessage());
			return null;
		}
	}

	/**
	 * Normalize a period to facilitate its comparison with another one
	 *
	 * @param period ({@link MutablePeriod})
	 * @return ({@link Period})
	 * @see Period#normalizedStandard()
	 */
	public static Period normalizePeriod(final MutablePeriod period) {
		return normalizePeriod(period.toPeriod());
	}

	/**
	 * Normalize a period to facilitate its comparison with another one
	 *
	 * @param period ({@link Period})
	 * @return ({@link Period})
	 * @see Period#normalizedStandard()
	 */
	public static Period normalizePeriod(Period period) {
		period = period.normalizedStandard(PeriodType.yearMonthDayTime());
		while (period.getDays() >= 30) {
			period = period.minusDays(30);
			period = period.plusMonths(1);
		}
		while (period.getDays() < 0) {
			period = period.plusDays(30);
			period = period.minusMonths(1);
		}
		return period.normalizedStandard(PeriodType.yearMonthDayTime());
	}
}
