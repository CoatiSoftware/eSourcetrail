package io.coati.eSourcetrail.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
* Sample integration test. In Eclipse, right-click > Run As > JUnit-Plugin. <br/>
* In Maven CLI, run "mvn integration-test".
*/
public class ActivatorTest {

	@Test
	public void veryStupidTest() {
		assertEquals("io.coati.eSourcetrail",Activator.PLUGIN_ID);
	}
}