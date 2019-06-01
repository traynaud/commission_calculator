package com.malt.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

import com.malt.model.enums.Time;
import com.malt.utils.TimeUtils;

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

	@Test
	public void delayBetweenTest() {
		final OffsetDateTime dateTime1_A = OffsetDateTime.of(2018, 10, 4, 10, 9, 30, 0, ZoneOffset.UTC);
		final OffsetDateTime dateTime1_B = OffsetDateTime.of(2018, 10, 4, 10, 10, 20, 0, ZoneOffset.UTC);
		final Delay test1 = Delay.delayBetween(dateTime1_A, dateTime1_B);
		assertEquals(50, test1.getSeconds());
		assertEquals(0, test1.getMinutes());
		assertEquals(0, test1.getHours());
		assertEquals(0, test1.getDays());
		assertEquals(0, test1.getMonths());
		assertEquals(0, test1.getYears());

		final OffsetDateTime dateTime2_A = OffsetDateTime.of(2018, 10, 4, 10, 9, 30, 0, ZoneOffset.UTC);
		final OffsetDateTime dateTime2_B = OffsetDateTime.of(2017, 12, 21, 10, 9, 30, 0, ZoneOffset.UTC);
		final Delay test2 = Delay.delayBetween(dateTime2_A, dateTime2_B);
		assertEquals(0, test2.getSeconds());
		assertEquals(0, test2.getMinutes());
		assertEquals(0, test2.getHours());
		assertEquals(13, test2.getDays());
		assertEquals(9, test2.getMonths());
		assertEquals(0, test2.getYears());

		final OffsetDateTime dateTime3_A = OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC);
		final OffsetDateTime dateTime3_B = OffsetDateTime.of(2017, 12, 21, 10, 0, 5, 0, ZoneOffset.UTC);
		final Delay test3 = Delay.delayBetween(dateTime3_A, dateTime3_B);
		assertEquals(55, test3.getSeconds());
		assertEquals(59, test3.getMinutes());
		assertEquals(22, test3.getHours());
		assertEquals(12, test3.getDays());
		assertEquals(9, test3.getMonths());
		assertEquals(0, test3.getYears());

		final OffsetDateTime dateTime4_A = OffsetDateTime.of(2018, 10, 4, 9, 0, 5, 0, ZoneOffset.UTC);
		final OffsetDateTime dateTime4_B = OffsetDateTime.of(2017, 12, 21, 10, 0, 0, 0, ZoneOffset.UTC);
		final Delay test4 = Delay.delayBetween(dateTime4_A, dateTime4_B);
		assertEquals(5, test4.getSeconds());
		assertEquals(0, test4.getMinutes());
		assertEquals(23, test4.getHours());
		assertEquals(12, test4.getDays());
		assertEquals(9, test4.getMonths());
		assertEquals(0, test4.getYears());

		final OffsetDateTime dateTime5_A = TimeUtils.parseDateTimeFromString("2018-04-16 13:24:17.510Z");
		final OffsetDateTime dateTime5_B = TimeUtils.parseDateTimeFromString("2018-07-16 14:24:17.510Z");
		final Delay test5 = Delay.delayBetween(dateTime5_A, dateTime5_B);
		assertEquals(0, test5.getSeconds());
		assertEquals(0, test5.getMinutes());
		assertEquals(1, test5.getHours());
		assertEquals(0, test5.getDays());
		assertEquals(3, test5.getMonths());
		assertEquals(0, test5.getYears());
	}
}
