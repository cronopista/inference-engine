package com.cronopista.ai.consistency;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.cronopista.ai.builders.TheoryBuilder;
import com.cronopista.ai.core.Statement;
import com.cronopista.ai.core.Theory;
import com.cronopista.ai.datasources.DataSource;
import com.cronopista.ai.errors.UnequalDataException;

public class ConsistencyDetectorTest {

	private static final String BLOCK = "█";

	@Test
	public void fillGaps() throws IOException, UnequalDataException {
		TheoryBuilder theory = new TheoryBuilder(new DataSource() {

			Random random = new Random();
			int countTotal = 0;

			public Statement next() throws UnequalDataException {
				countTotal++;
				if (countTotal > 100) {
					return null;
				}

				int grossSales = random.nextInt(100);
				int yearsInBusiness = random.nextInt(10);
				String label = "false";
				if (grossSales > 60 && yearsInBusiness > 5) {
					label = "true";
				}

				return new Statement(label, grossSales, yearsInBusiness);

			}

			public List<String> getLabels() {

				return null;
			}
		});

		Theory builtTheory = theory.buildTheory();
		System.out.println(builtTheory.prettyPrint(Arrays.asList(new String[] { "grossSales", "yearsInBusiness" })));

		for (int j = 0; j < 10; j++) {
			String blocks = j + " [";
			for (int i = 0; i < 99; i++) {
				String eval = builtTheory.evalTotal(new Statement("", i, j));
				if ("true".equals(eval)) {
					blocks += BLOCK;
				} else if ("false".equals(eval)) {
					blocks += " ";
				} else {
					blocks += "X";
				}
			}
			System.out.println(blocks + "]");
		}

	}

}
