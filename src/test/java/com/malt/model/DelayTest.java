package com.malt.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.joda.time.Period;
import org.junit.Test;

import com.malt.model.enums.Time;

/**
 * Test the well behavior of the class {@link Delay}
 *
 * @author Tanguy
 * @version 1.0
 * @since 31 May 2019
 * @see Delay
 *
 */
public class DelayTest {

	@Test
	public void fromStringTest() {
		final Delay d1 = Delay.fromString("10x2seconds");
		assertNull(d1);
		final Delay d2 = Delay.fromString("12minuts&2seconds");
		assertNull(d2);
		final Delay d3 = Delay.fromString("1second");
		assertEquals(1, d3.getSeconds());
		assertEquals(0, d3.getMinutes());
		assertEquals(0, d3.getHours());
		assertEquals(0, d3.getDays());
		assertEquals(0, d3.getMonths());
		assertEquals(0, d3.getYears());

		final Delay d4 = Delay.fromString("1minute");
		assertEquals(0, d4.getSeconds());
		assertEquals(1, d4.getMinutes());
		assertEquals(0, d4.getHours());
		assertEquals(0, d4.getDays());
		assertEquals(0, d4.getMonths());
		assertEquals(0, d4.getYears());

		final Delay d5 = Delay.fromString("1hour");
		assertEquals(0, d5.getSeconds());
		assertEquals(0, d5.getMinutes());
		assertEquals(1, d5.getHours());
		assertEquals(0, d5.getDays());
		assertEquals(0, d5.getMonths());
		assertEquals(0, d5.getYears());

		final Delay d6 = Delay.fromString("1day");
		assertEquals(0, d6.getSeconds());
		assertEquals(0, d6.getMinutes());
		assertEquals(0, d6.getHours());
		assertEquals(1, d6.getDays());
		assertEquals(0, d6.getMonths());
		assertEquals(0, d6.getYears());

		final Delay d7 = Delay.fromString("1month");
		assertEquals(0, d7.getSeconds());
		assertEquals(0, d7.getMinutes());
		assertEquals(0, d7.getHours());
		assertEquals(0, d7.getDays());
		assertEquals(1, d7.getMonths());
		assertEquals(0, d7.getYears());

		final Delay d8 = Delay.fromString("1year");
		assertEquals(0, d8.getSeconds());
		assertEquals(0, d8.getMinutes());
		assertEquals(0, d8.getHours());
		assertEquals(0, d8.getDays());
		assertEquals(0, d8.getMonths());
		assertEquals(1, d8.getYears());

		final Delay d9 = Delay.fromString("3months&2days");
		assertEquals(0, d9.getSeconds());
		assertEquals(0, d9.getMinutes());
		assertEquals(0, d9.getHours());
		assertEquals(2, d9.getDays());
		assertEquals(3, d9.getMonths());
		assertEquals(0, d9.getYears());

		final Delay d10 = Delay.fromString("4months2days10hours");
		assertEquals(0, d10.getSeconds());
		assertEquals(0, d10.getMinutes());
		assertEquals(10, d10.getHours());
		assertEquals(2, d10.getDays());
		assertEquals(4, d10.getMonths());
		assertEquals(0, d10.getYears());

		final Delay d11 = Delay.fromString("2hours 1minute 15seconds");
		assertEquals(15, d11.getSeconds());
		assertEquals(1, d11.getMinutes());
		assertEquals(2, d11.getHours());
		assertEquals(0, d11.getDays());
		assertEquals(0, d11.getMonths());
		assertEquals(0, d11.getYears());

		final Delay d12 = Delay.fromString("1year 5months&24days");
		assertEquals(0, d12.getSeconds());
		assertEquals(0, d12.getMinutes());
		assertEquals(0, d12.getHours());
		assertEquals(24, d12.getDays());
		assertEquals(5, d12.getMonths());
		assertEquals(1, d12.getYears());
	}

	@Test
	public void normalizePeriodTest() {
		try {
			final Method normalizePeriod = Delay.class.getDeclaredMethod("normalizePeriod", Period.class);
			normalizePeriod.setAccessible(true);

			final Delay d1 = new Delay();
			d1.addTime(Time.SECOND, 65);
			final Object r1 = normalizePeriod.invoke(null, d1.toPeriod());
			assertTrue(r1 instanceof Period);
			final Period p1 = (Period) r1;
			assertEquals(5, p1.getSeconds());
			assertEquals(1, p1.getMinutes());

			final Delay d2 = new Delay();
			d2.addTime(Time.SECOND, 135);
			d2.addTime(Time.MINUTE, 135);
			d2.addTime(Time.HOUR, 35);
			d2.addTime(Time.DAY, 32);
			d2.addTime(Time.MONTH, 14);
			d2.addTime(Time.YEAR, 1);
			final Object r2 = normalizePeriod.invoke(null, d2.toPeriod());
			assertTrue(r2 instanceof Period);
			final Period p2 = (Period) r2;
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
	public void compareToTest() {
		final Delay d1 = new Delay();
		final Delay d2 = new Delay();
		d1.addTime(Time.SECOND, 10);
		d2.addTime(Time.SECOND, 20);
		assertTrue(d1.compareTo(d2) < 0);

		d1.addTime(Time.MINUTE, 10);
		d2.addTime(Time.MINUTE, 9);
		assertTrue(d1.compareTo(d2) > 0);

		d1.addTime(Time.SECOND, 50);
		d2.addTime(Time.SECOND, 100);
		assertTrue(d1.compareTo(d2) == 0);

		final Delay d3 = new Delay();
		final Delay d4 = new Delay();
		d3.addTime(Time.YEAR, 1);
		d4.addTime(Time.SECOND, 1);
		assertTrue(d3.compareTo(d4) > 0);
		d4.addTime(Time.MINUTE, 1);
		assertTrue(d3.compareTo(d4) > 0);
		d4.addTime(Time.HOUR, 1);
		assertTrue(d3.compareTo(d4) > 0);
		d4.addTime(Time.DAY, 1);
		assertTrue(d3.compareTo(d4) > 0);
		d4.addTime(Time.MONTH, 1);
		assertTrue(d3.compareTo(d4) > 0);
		d4.addTime(Time.YEAR, 1);
		assertTrue(d3.compareTo(d4) < 0);
	}

	@Test
	public void addTimeTest() {
		final Delay delay = new Delay();
		delay.addTime(Time.SECOND, 10);
		assertEquals(10, delay.getSeconds());
		delay.addTime(Time.SECOND, 5);
		assertEquals(15, delay.getSeconds());

		delay.addTime(Time.MINUTE, 10);
		assertEquals(10, delay.getMinutes());
		delay.addTime(Time.MINUTE, 5);
		assertEquals(15, delay.getMinutes());

		delay.addTime(Time.HOUR, 10);
		assertEquals(10, delay.getHours());
		delay.addTime(Time.HOUR, 5);
		assertEquals(15, delay.getHours());

		delay.addTime(Time.DAY, 10);
		assertEquals(10, delay.getDays());
		delay.addTime(Time.DAY, 5);
		assertEquals(15, delay.getDays());

		delay.addTime(Time.MONTH, 10);
		assertEquals(10, delay.getMonths());
		delay.addTime(Time.MONTH, 5);
		assertEquals(15, delay.getMonths());

		delay.addTime(Time.YEAR, 10);
		assertEquals(10, delay.getYears());
		delay.addTime(Time.YEAR, 5);
		assertEquals(15, delay.getYears());
	}

	@Test
	public void UnitsConversionTest() {
		// MINUT
		final Delay d_s1 = new Delay();
		final Delay d_m1 = new Delay();
		d_s1.addTime(Time.SECOND, 60);
		d_m1.addTime(Time.MINUTE, 1);
		assertTrue(d_s1.compareTo(d_m1) == 0);

		// HOUR
		final Delay d_s2 = new Delay();
		final Delay d_m2 = new Delay();
		final Delay d_h2 = new Delay();
		d_s2.addTime(Time.SECOND, 3600);
		d_m2.addTime(Time.MINUTE, 60);
		d_h2.addTime(Time.HOUR, 1);
		assertTrue(d_s2.compareTo(d_m2) == 0);
		assertTrue(d_s2.compareTo(d_h2) == 0);
		assertTrue(d_m2.compareTo(d_h2) == 0);

		// DAY
		final Delay d_s3 = new Delay();
		final Delay d_m3 = new Delay();
		final Delay d_h3 = new Delay();
		final Delay d_d3 = new Delay();
		d_s3.addTime(Time.SECOND, 86400);
		d_m3.addTime(Time.MINUTE, 1440);
		d_h3.addTime(Time.HOUR, 24);
		d_d3.addTime(Time.DAY, 1);
		assertTrue(d_s3.compareTo(d_m3) == 0);
		assertTrue(d_s3.compareTo(d_h3) == 0);
		assertTrue(d_s3.compareTo(d_d3) == 0);
		assertTrue(d_m3.compareTo(d_h3) == 0);
		assertTrue(d_m3.compareTo(d_d3) == 0);
		assertTrue(d_h3.compareTo(d_d3) == 0);

		// MONTH
		final Delay d_s4 = new Delay();
		final Delay d_m4 = new Delay();
		final Delay d_h4 = new Delay();
		final Delay d_d4 = new Delay();
		final Delay d_M4 = new Delay();
		d_s4.addTime(Time.SECOND, 2592000);
		d_m4.addTime(Time.MINUTE, 43200);
		d_h4.addTime(Time.HOUR, 720);
		d_d4.addTime(Time.DAY, 30);
		d_M4.addTime(Time.MONTH, 1);
		assertTrue(d_s4.compareTo(d_m4) == 0);
		assertTrue(d_s4.compareTo(d_h4) == 0);
		assertTrue(d_s4.compareTo(d_d4) == 0);
		assertTrue(d_s4.compareTo(d_M4) == 0);
		assertTrue(d_m4.compareTo(d_h4) == 0);
		assertTrue(d_m4.compareTo(d_d4) == 0);
		assertTrue(d_m4.compareTo(d_M4) == 0);
		assertTrue(d_h4.compareTo(d_d4) == 0);
		assertTrue(d_h4.compareTo(d_M4) == 0);
		assertTrue(d_d4.compareTo(d_M4) == 0);

		// YEAR
		final Delay d_s5 = new Delay();
		final Delay d_m5 = new Delay();
		final Delay d_h5 = new Delay();
		final Delay d_d5 = new Delay();
		final Delay d_M5 = new Delay();
		final Delay d_y5 = new Delay();
		d_s5.addTime(Time.SECOND, 31104000);
		d_m5.addTime(Time.MINUTE, 518400);
		d_h5.addTime(Time.HOUR, 8640);
		d_d5.addTime(Time.DAY, 360);
		d_M5.addTime(Time.MONTH, 12);
		d_y5.addTime(Time.YEAR, 1);
		assertTrue(d_s5.compareTo(d_m5) == 0);
		assertTrue(d_s5.compareTo(d_h5) == 0);
		assertTrue(d_s5.compareTo(d_d5) == 0);
		assertTrue(d_s5.compareTo(d_M5) == 0);
		assertTrue(d_s5.compareTo(d_y5) == 0);
		assertTrue(d_m5.compareTo(d_h5) == 0);
		assertTrue(d_m5.compareTo(d_d5) == 0);
		assertTrue(d_m5.compareTo(d_M5) == 0);
		assertTrue(d_m5.compareTo(d_y5) == 0);
		assertTrue(d_h5.compareTo(d_d5) == 0);
		assertTrue(d_h5.compareTo(d_M5) == 0);
		assertTrue(d_h5.compareTo(d_y5) == 0);
		assertTrue(d_d5.compareTo(d_M5) == 0);
		assertTrue(d_d5.compareTo(d_y5) == 0);
		assertTrue(d_M5.compareTo(d_y5) == 0);
	}
}
