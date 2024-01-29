package com.cronopista.ai.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.cronopista.ai.builders.TheoryBuilder;
import com.cronopista.ai.datasources.DataSource;
import com.cronopista.ai.errors.UnequalDataException;

public class NumbersOnlyTheoryTest {

	@Test
	public void testBasicBarTheory() throws IOException, UnequalDataException {
		TheoryBuilder theory = new TheoryBuilder(new DataSource() {

			Random random = new Random();
			int countTotal = 0;

			public Statement next() throws UnequalDataException {
				countTotal++;
				if (countTotal > 1000) {
					return null;
				}

				int age = random.nextInt(100);
				int accompaniedByAdult = random.nextInt(2);
				String label = "false";
				if (age >= 18 || accompaniedByAdult == 1) {
					label = "true";
				}

				return new Statement(label, age, accompaniedByAdult);

			}

			public List<String> getLabels() {

				return null;
			}
		});

		Theory builtTheory = theory.buildTheory();
		System.out.println(builtTheory.prettyPrint(Arrays.asList(new String[] { "age", "accompanied" })));
		System.out.println(builtTheory.getSteps());
	}

	@Test
	public void testRandomTheory() throws IOException, UnequalDataException {
		TheoryBuilder theory = new TheoryBuilder(new DataSource() {

			Random random = new Random();
			int countTotal = 0;

			public Statement next() throws UnequalDataException {
				countTotal++;
				if (countTotal > 1000) {
					return null;
				}

				int age = random.nextInt(100);
				int accompaniedByAdult = random.nextInt(2);
				String label = "false";
				if (age >= 18 || accompaniedByAdult == 1) {
					label = "true";
				}

				return new Statement(label, accompaniedByAdult, random.nextDouble());

			}

			public List<String> getLabels() {

				return null;
			}
		});

		Theory builtTheory = theory.buildTheory();
		System.out.println(builtTheory.prettyPrint(Arrays.asList(new String[] { "age", "accompanied" })));
		System.out.println(builtTheory.getSteps());
	}

	@Test
	public void testBarTheoryWithId() throws UnequalDataException {
		TheoryBuilder theory = new TheoryBuilder(new DataSource() {

			Random random = new Random();
			int countTotal = 0;

			public Statement next() throws UnequalDataException {
				countTotal++;
				if (countTotal > 1000) {
					return null;
				}

				int age = random.nextInt(100);
				int accompaniedByAdult = random.nextInt(2);
				int hasId = random.nextInt(2);
				String label = "false";
				if ((age >= 18 && hasId == 1) || accompaniedByAdult == 1) {
					label = "true";
				}

				return new Statement(label, age, hasId, accompaniedByAdult);

			}

			public List<String> getLabels() {

				return null;
			}
		});

		Theory builtTheory = theory.buildTheory();
		System.out.println(builtTheory.prettyPrint(Arrays.asList(new String[] { "age", "hasId", "accompanied" })));
		System.out.println(builtTheory.getSteps());

	}

	@Test
	public void testOverlappingConditions() throws UnequalDataException {
		TheoryBuilder theory = new TheoryBuilder(new DataSource() {

			Random random = new Random();
			int countTotal = 0;

			public Statement next() throws UnequalDataException {
				countTotal++;
				if (countTotal > 100000) {
					return null;
				}

				int a1 = random.nextInt(6);
				int a2 = random.nextInt(6);
				int a3 = random.nextInt(6);
				int a4 = random.nextInt(6);
				String label = "a1";
				if (a1 == 1) {
					label = "a1";
				}
				if (a2 == 2) {
					label = "a2";
				}

				if (a3 == 3) {
					label = "a3";
				}

				if (a4 == 4) {
					label = "a4";
				}

				if (a1 == 1 && a2 == 2) {
					label = "a4";
				}

				if (a1 == 5 && a3 == 2) {
					label = "a3";
				}

				return new Statement(label, a1, a2, a3, a4);

			}

			public List<String> getLabels() {

				return null;
			}
		});

		Theory builtTheory = theory.buildTheory();
		System.out.println(builtTheory.prettyPrint(Arrays.asList(new String[] { "a1", "a2", "a3", "a4" })));
		System.out.println(builtTheory.getSteps());
	}

	@Test
	public void testSeveralRanges() throws UnequalDataException {
		TheoryBuilder theory = new TheoryBuilder(new DataSource() {

			Random random = new Random();
			int countTotal = 0;

			public Statement next() throws UnequalDataException {
				countTotal++;
				if (countTotal > 100000) {
					return null;
				}

				int firstRange = random.nextInt(100);
				int secondRange = random.nextInt(100);
				int yesno = random.nextInt(2);
				String label = "false";
				if (firstRange >= 21 && firstRange <= 80
						&& ((secondRange > 30 && yesno == 0) || (secondRange > 70 && yesno == 1))) {
					label = "true";
				}

				return new Statement(label, firstRange, yesno, secondRange);

			}

			public List<String> getLabels() {

				return null;
			}
		});

		Theory builtTheory = theory.buildTheory();
		System.out
				.println(builtTheory.prettyPrint(Arrays.asList(new String[] { "firstRange", "yesno", "secondRange" })));
		System.out.println(builtTheory.getSteps());

	}

	@Test
	public void testInterralatedFractionesRanges() throws UnequalDataException {

		TheoryBuilder theory = new TheoryBuilder(new DataSource() {

			Random random = new Random();
			int countTotal = 0;

			public Statement next() throws UnequalDataException {
				countTotal++;
				if (countTotal > 100000) {
					return null;
				}

				int firstRange = random.nextInt(100);
				int secondRange = random.nextInt(100);
				int randomRange = random.nextInt(100);
				String label = "false";
				if (((firstRange >= 20 && firstRange <= 40 && (secondRange <= 20 || secondRange >= 40)))
						|| (((firstRange <= 20 || firstRange >= 40) && (secondRange >= 20 && secondRange <= 40)))) {
					label = "true";
				}

				return new Statement(label, firstRange, randomRange, secondRange);

			}

			public List<String> getLabels() {

				return null;
			}
		});

		Theory builtTheory = theory.buildTheory();
		System.out.println(
				builtTheory.prettyPrint(Arrays.asList(new String[] { "firstRange", "randomRange", "secondRange" })));
		System.out.println(builtTheory.getSteps());

	}

}
