package test;

import junit.framework.Assert;

import org.junit.Test;

import uit.qass.gate.GateUtil;

public class GateTest {

	public GateTest() {
		GateUtil.RunGate();
	}
	
	@Test
	public void testGazetteer1() {		
		String actual = GateUtil.getGazeteerClass("Tom Sawyer");
		String expected = "novel";
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGazetteer2() {		
		String actual = GateUtil.getGazeteerClass("Mark Twain");
		String expected = "author";
		Assert.assertEquals(expected, actual);
	}
}
