package com.cronopista.ai.purger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cronopista.ai.builders.TheoryBuilder;
import com.cronopista.ai.core.Range;
import com.cronopista.ai.core.Statement;
import com.cronopista.ai.core.Theory;
import com.cronopista.ai.datasources.DataSource;
import com.cronopista.ai.errors.UnequalDataException;

public class PermutationalDataPurger {

	private DataSource ds;

	public PermutationalDataPurger(DataSource ds) {
		this.ds = ds;
	}

	public List<PurgeInfo> find() throws IOException, UnequalDataException {
		List<String> labels = ds.getLabels();
		List<Statement> allStatements = new ArrayList<Statement>();
		Statement newStatemen = null;
		while ((newStatemen = ds.next()) != null) {
			allStatements.add(newStatemen);
		}

		Collections.shuffle(allStatements);

		List<PurgeInfo> combinations = new ArrayList<PurgeInfo>();
		for (int i = 0; i < labels.size(); i++) {
			combinations.addAll(tryCombinations(allStatements, labels.size(), new ArrayList<Integer>(), i));
		}

		Collections.sort(combinations);

		return combinations;
	}

	private List<PurgeInfo> tryCombinations(List<Statement> statements, int total, List<Integer> partial, int index)
			throws UnequalDataException {

		List<Integer> added = new ArrayList<Integer>();
		added.addAll(partial);
		added.add(index);

		PurgerDataSource purgerDataSource = new PurgerDataSource(statements, added);
		TheoryBuilder builder = new TheoryBuilder(purgerDataSource);
		Theory theory = builder.buildTheory();

		System.out.println(added + " " + theory.getSteps());

		List<PurgeInfo> result = new ArrayList<PurgeInfo>();
		result.add(new PurgeInfo(added, theory.getSteps()));

		for (int i = index + 1; i < total; i++) {
			result.addAll(tryCombinations(statements, total, added, i));
		}

		return result;
	}

	public class PurgeInfo implements Comparable<PurgeInfo> {

		private List<Integer> selection;

		private int steps;

		public PurgeInfo(List<Integer> selection, int steps) {
			super();
			this.selection = selection;
			this.steps = steps;
		}

		public int compareTo(PurgeInfo o) {

			return steps - o.steps;
		}

		public int getSteps() {
			return steps;
		}

		public List<Integer> getSelection() {
			return selection;
		}

		@Override
		public String toString() {
			return "PurgeInfo [steps=" + steps + ", selection=" + selection + "]\n";
		}

	}

	private class PurgerDataSource implements DataSource {

		private List<Statement> statements;

		private List<Integer> subselction;

		private int index;

		public PurgerDataSource(List<Statement> statements, List<Integer> subselction) {
			super();
			this.statements = statements;
			this.subselction = subselction;
		}

		public Statement next() throws UnequalDataException {
			if (index >= statements.size()) {
				return null;
			}

			Statement oldStatement = statements.get(index);
			Statement newStatement = new Statement(oldStatement.getLabel());
			for (Integer i : subselction) {
				Range oldRange = oldStatement.getData().get(i);
				newStatement.addData(oldRange.merge(oldRange));
			}

			index++;

			return newStatement;
		}

		public List<String> getLabels() {

			return null;
		}

	}

}
