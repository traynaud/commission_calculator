package com.malt.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.joda.time.Period;
import org.junit.Test;

import com.malt.model.Delay;
import com.malt.model.enums.Time;

/**
 * Test the well behavior of the class {@link TimeUtils}
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 * @see Delay
 *
 */
public class TimeUtilsTest {

	@Test
	public void normalizePeriodTest() {
		try {

			final Delay d1 = new Delay();
			d1.addTime(Time.SECOND, 65);
			final Period p1 = TimeUtils.normalizePeriod(d1.toPeriod());
			assertEquals(5, p1.getSeconds());
			assertEquals(1, p1.getMinutes());

			final Delay d2 = new Delay();
			d2.addTime(Time.SECOND, 135);
			d2.addTime(Time.MINUTE, 135);
			d2.addTime(Time.HOUR, 35);
			d2.addTime(Time.DAY, 32);
			d2.addTime(Time.MONTH, 14);
			d2.addTime(Time.YEAR, 1);
			final Period p2 = TimeUtils.normalizePeriod(d2.toPeriod());
			assertEquals(15, p2.getSeconds());
			assertEquals(17, p2.getMinutes());
			assertEquals(13, p2.getHours());
			assertEquals(3, p2.getDays());
			assertEquals(3, p2.getMonths());
			assertEquals(2, p2.getYears());

		} catch (final Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail(e.getMessage());
		}
	}

	@Test
	public void parseDateTimeFromStringTest() {
		assertNotNull(TimeUtils.parseDateTimeFromString("2018-04-16 13:24:17.510Z"));
		assertNotNull(TimeUtils.parseDateTimeFromString("2018-07-16 14:24:17.510Z"));
	}
}
