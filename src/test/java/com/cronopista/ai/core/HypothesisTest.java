package com.cronopista.ai.core;

import org.junit.Assert;
import org.junit.Test;

public class HypothesisTest {

	@Test
	public void testHypothesis() {
		Hypothesis h = new Hypothesis("true");
		Assert.assertFalse(h.isConsistent(new Statement("true", 35, 0)));

		h.addStatement(new Statement("true", 18, 0));
		h = h.mergeToStatement(0, new Statement("true", 38, 0));

		Assert.assertTrue(h.isConsistent(new Statement("true", 35, 0)));
		Assert.assertTrue(h.isConsistent(new Statement("false", 90, 0)));
		Assert.assertFalse(h.isConsistent(new Statement("false", 35, 0)));
		Assert.assertFalse(h.isConsistent(new Statement("true", 90, 0)));

	}

}
