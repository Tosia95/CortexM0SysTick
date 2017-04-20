package cortexM0;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CortexM0SysTickTest {
	/**
	 * Default constructor for test class CortexM0SysTickTest
	 */
	public CortexM0SysTickTest() {
	}

	@Test
	public void testSetRVR() {
		CortexM0SysTick cortexM01 = new CortexM0SysTick();
		cortexM01.setRVR(5);
		cortexM01.setCVR(2);
		cortexM01.impuls();
		assertEquals(0, cortexM01.getCVR());
		cortexM01.setEnableFlag(true);
		cortexM01.impuls();
		assertEquals(5, cortexM01.getCVR());
		for (int i = 0; i < 7; i++)
			cortexM01.impuls();
		assertEquals(4, cortexM01.getCVR());
		assertEquals(true, cortexM01.isEnableFlag());
	}

	@Test
	public void testSetCVR() {
		CortexM0SysTick cortexM01 = new CortexM0SysTick();
		cortexM01.setCVR(2);
		assertEquals(0, cortexM01.getCVR());
		assertEquals(false, cortexM01.isCountFlag());
	}

	@Test
	public void testTicking() {
		CortexM0SysTick cortexM01 = new CortexM0SysTick();
		cortexM01.setTickingFlag(true);
		cortexM01.isTickingFlag();
		assertEquals(false, cortexM01.isCountFlag());
		assertEquals(true, cortexM01.isTickingFlag());
	}

	@Test
	public void testEnableFlag() {

		CortexM0SysTick cortexM01 = new CortexM0SysTick();
		cortexM01.setEnableFlag(true);
		cortexM01.setRVR(5);
		cortexM01.setCVR(2);
		// cortexM01.impuls();
		assertEquals(0, cortexM01.getCVR());

		for (int i = 0; i < 3; i++) {
			cortexM01.impuls();
		}

		assertEquals(3, cortexM01.getCVR());

		cortexM01.setEnableFlag(false);
		cortexM01.impuls();
		assertEquals(3, cortexM01.getCVR());
	}

	@Test
	public void testCountFlag() {
		CortexM0SysTick cortexM01 = new CortexM0SysTick();
		cortexM01.setEnableFlag(true);
		cortexM01.setRVR(5);
		cortexM01.setCVR(2);
		assertEquals(0, cortexM01.getCVR());
		assertEquals(false, cortexM01.isCountFlag());
		cortexM01.impuls();
		assertEquals(5, cortexM01.getCVR());
		assertEquals(true, cortexM01.isCountFlag());
		}
}
	
