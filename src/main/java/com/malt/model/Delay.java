package com.malt.model;

import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.malt.model.enums.Time;
import com.malt.utils.TimeUtils;

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
	private static final String REGEX_DELAY = "(\\d+)(([A-Za-z]+))";
	private static final String REGEX_MULTIPLE_DELAY = "(" + REGEX_DELAY + "(\\s+|[&_\\-\\+,;\\:])?)+";

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

	@Override
	public int compareTo(final Delay o) {
		final Period norm = TimeUtils.normalizePeriod(toPeriod());
		final Period o_norm = TimeUtils.normalizePeriod(o.toPeriod());
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		if (years > 0) {
			sb.append(Integer.toString(years)).append(Time.YEAR.name().toLowerCase()).append(years > 1 ? 's' : "");
		}
		if (months > 0) {
			sb.append(Integer.toString(months)).append(Time.MONTH.name().toLowerCase()).append(months > 1 ? 's' : "");
		}
		if (days > 0) {
			sb.append(Integer.toString(days)).append(Time.DAY.name().toLowerCase()).append(days > 1 ? 's' : "");
		}
		if (hours > 0) {
			sb.append(Integer.toString(hours)).append(Time.HOUR.name().toLowerCase()).append(hours > 1 ? 's' : "");
		}
		if (minutes > 0) {
			sb.append(Integer.toString(minutes)).append(Time.MINUTE.name().toLowerCase())
					.append(minutes > 1 ? 's' : "");
		}
		if (seconds > 0) {
			sb.append(Integer.toString(seconds)).append(Time.SECOND.name().toLowerCase())
					.append(seconds > 1 ? 's' : "");
		}
		return sb.toString();
	}

	/**
	 * Convert the current object into a {@link Period}
	 *
	 * @return ({@link Period})
	 */
	public Period toPeriod() {
		return new Period(years, months, 0, days, hours, minutes, seconds, 0);
	}

	/**
	 * Create a new {@link Delay} from a {@link Period}
	 *
	 * @param period ({@link Period})
	 * @return {@link Delay}
	 */
	public static Delay fromPeriod(final Period period) {
		final Delay delay = new Delay();
		delay.setYears(period.getYears());
		delay.setMonths(period.getMonths());
		delay.setDays(period.getDays());
		delay.setHours(period.getHours());
		delay.setMinutes(period.getMinutes());
		delay.setSeconds(period.getSeconds());
		return delay;
	}

	/**
	 * Create a new {@link Delay} from a string
	 *
	 * @param str ({@link String}) A string representing a delay.
	 * @return {@link Delay}
	 * @see #REGEX_DELAY
	 */
	public static Delay fromString(final String str) {
		if (!str.matches(REGEX_MULTIPLE_DELAY)) {
			logger.warn("'{}' is not a valid Delay : expected '{}'", str, REGEX_MULTIPLE_DELAY);
			return null;
		}
		final Delay delay = new Delay();
		final Pattern pattern = Pattern.compile(REGEX_DELAY);
		final Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			final int value;
			try {
				value = Integer.parseInt(matcher.group(1));
			} catch (final NumberFormatException e) {
				logger.warn("An error occured with group '{}': Unable to convert '{}' into a number!", matcher.group(0),
						matcher.group(1));
				return null;
			}
			final Time time = Time.fromString(matcher.group(2));
			if (time == null) {
				logger.warn("An error occured with group '{}': '{}' is not a valid time value", matcher.group(0),
						matcher.group(2));
				return null;
			}
			delay.addTime(time, value);
		}
		return delay;
	}

	/**
	 * Create a new {@link Delay} from a {@link Period}
	 *
	 * @param dateTime ({@link Period})
	 * @return {@link Delay}
	 */
	private static Delay fromDateTime(final OffsetDateTime dateTime) {
		final Delay delay = new Delay();
		delay.setYears(dateTime.getYear());
		delay.setMonths(dateTime.getMonthValue());
		delay.setDays(dateTime.getDayOfMonth());
		delay.setHours(dateTime.getHour());
		delay.setMinutes(dateTime.getMinute());
		delay.setSeconds(dateTime.getSecond());
		return delay;
	}

	/**
	 * Compute a {@link Delay} between two dates<br/>
	 * The delay is always strictly positive, even if the first date is after the
	 * second
	 *
	 * @param date1 ({@link OffsetDateTime})
	 * @param date2 ({@link OffsetDateTime})
	 * @return {@link Delay}
	 */
	public static Delay delayBetween(final OffsetDateTime date1, final OffsetDateTime date2) {
		final Delay delay1 = fromDateTime(date1);
		final Delay delay2 = fromDateTime(date2);
		final int compareTo = delay1.compareTo(delay2);
		final Period max;
		final Period min;
		if (compareTo == 0) {
			return new Delay();
		} else if (compareTo < 0) {
			max = delay2.toPeriod();
			min = delay1.toPeriod();
		} else {
			max = delay1.toPeriod();
			min = delay2.toPeriod();
		}
		final Period diff = max.minus(min);
		return Delay.fromPeriod(TimeUtils.normalizePeriod(diff));
	}
}
