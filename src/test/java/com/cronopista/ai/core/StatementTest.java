package com.cronopista.ai.core;

import org.junit.Assert;
import org.junit.Test;

public class StatementTest {
	
	@Test
	public void testStatementMerge() {
		Statement first = new Statement("true");
		first.addData(12d);
		first.addData(5d);
		
		Statement second = new Statement("true");
		second.addData(6d);
		second.addData(8d);
		
		Statement merged = first.merge(second);
		Assert.assertEquals(6d, merged.getData().get(0).getFrom(), 0.1d);
		Assert.assertEquals(12d, merged.getData().get(0).getTo(), 0.1d);
		Assert.assertEquals(5d, merged.getData().get(1).getFrom(), 0.1d);
		Assert.assertEquals(8d, merged.getData().get(1).getTo(), 0.1d);
	}

	
	@Test
	public void testStatementEvaluation() {
		Statement first = new Statement("true");
		first.addData(12d);
		first.addData(5d);
		
		Statement second = new Statement("true");
		second.addData(6d);
		second.addData(8d);
		
		Statement merged = first.merge(second);
	
		Assert.assertTrue(merged.evalTotal(first));
		Assert.assertTrue(merged.evalTotal(second));
		
		Statement third = new Statement("true");
		third.addData(8d);
		third.addData(7d);
		
		Assert.assertTrue(merged.evalTotal(third));
		
		
	}
}
