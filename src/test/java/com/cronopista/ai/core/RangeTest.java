package com.cronopista.ai.core;

import org.junit.Assert;
import org.junit.Test;

public class RangeTest {

	@Test
	public void testMergeTo() {
		Range range = new Range(2d, 10.3d);
		Range merged = range.merge(new Range(2.1d, 2000d));

		Assert.assertEquals(2d, merged.getFrom(), 0.1);
		Assert.assertEquals(2000d, merged.getTo(), 0.1);
	}

	@Test
	public void testMergeFrom() {
		Range range = new Range(2d, 10.3d);
		Range merged = range.merge(new Range(1.1d, 9d));

		Assert.assertEquals(1.1d, merged.getFrom(), 0.1);
		Assert.assertEquals(10.3d, merged.getTo(), 0.1);
	}
}
