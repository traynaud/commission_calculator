package com.malt.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test of the Class {@link LocalisationService}
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
public class LocalisationServiceTest {

	@Test
	public void checkIPAdressTest() {
		try {
			final Method checkIPAdress = LocalisationService.class.getDeclaredMethod("checkIPAdress", String.class);
			checkIPAdress.setAccessible(true);

			final Object r1 = checkIPAdress.invoke(null, "");
			assertTrue(r1 instanceof Boolean);
			assertFalse((boolean) r1);

			final Object r2 = checkIPAdress.invoke(null, "192.");
			assertTrue(r2 instanceof Boolean);
			assertFalse((boolean) r2);

			final Object r3 = checkIPAdress.invoke(null, "192.257.255.255");
			assertTrue(r3 instanceof Boolean);
			assertFalse((boolean) r3);

			final Object r4 = checkIPAdress.invoke(null, "192.168.247.1024");
			assertTrue(r4 instanceof Boolean);
			assertFalse((boolean) r4);

			final Object r5 = checkIPAdress.invoke(null, "1111.123.123.123");
			assertTrue(r5 instanceof Boolean);
			assertFalse((boolean) r5);

			final Object r6 = checkIPAdress.invoke(null, "0.0.0.0");
			assertTrue(r6 instanceof Boolean);
			assertTrue((boolean) r6);

			final Object r7 = checkIPAdress.invoke(null, "255.255.255.255");
			assertTrue(r7 instanceof Boolean);
			assertTrue((boolean) r7);

			final Object r8 = checkIPAdress.invoke(null, "192.168.1.5");
			assertTrue(r8 instanceof Boolean);
			assertTrue((boolean) r8);

		} catch (final Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			fail(e.getMessage());
		}
	}

}
