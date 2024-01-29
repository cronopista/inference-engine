package com.cronopista.ai.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Theory implements Comparable<Theory> {

	private List<Statement> history;

	private Map<String, Hypothesis> hypotheses;

	private int steps;

	public Theory() {
		history = new ArrayList<Statement>();
		hypotheses = new HashMap<String, Hypothesis>();
	}

	public void addToHistory(Statement s) {

		history.add(s);
		if (!hypotheses.containsKey(s.getLabel())) {
			hypotheses.put(s.getLabel(), new Hypothesis(s.getLabel()));
		}

	}

	public int countTotalStatements() {
		int total = 0;
		for (Hypothesis hypothesis : hypotheses.values()) {
			total += hypothesis.getStatements().size();
		}

		return total;
	}

	public boolean addStatement(Statement s) {
		boolean changed = false;

		Hypothesis hypothesis = hypotheses.get(s.getLabel());

		if (!hypothesis.isConsistent(s)) {
			resolveInconsistency(s, hypothesis);
			changed = true;
			steps++;
		}

		return changed;
	}

	private void resolveInconsistency(Statement s, Hypothesis hypothesis) {
		Hypothesis newHypothesis = expandHypothesis(hypothesis, s);
		if (newHypothesis == null) {
			hypothesis.addStatement(s);
			Collections.sort(hypothesis.getStatements());
		} else {
			hypothesis.setStatements(newHypothesis.getStatements());
		}
	}

	private Hypothesis expandHypothesis(Hypothesis hypothesis, Statement s) {
		for (int i = 0; i < hypothesis.getStatements().size(); i++) {
			Hypothesis newHypothesis = hypothesis.mergeToStatement(i, s);
			if (!isHypothesisFalsifiedByHistory(newHypothesis)) {
				hypothesis.getStatements().get(i).addHit();
				return newHypothesis;
			}
		}

		return null;
	}

	private boolean isHypothesisFalsifiedByHistory(Hypothesis hypothesis) {
		for (Statement statement : history) {
			if (!statement.getLabel().equals(hypothesis.getLabel()) && !hypothesis.isConsistent(statement)) {
				return true;
			}
		}

		return false;
	}

	public int getSteps() {
		return steps;
	}

	public String prettyPrint(List<String> labels) {
		StringBuilder result = new StringBuilder();
		for (Hypothesis hypothesis : hypotheses.values()) {
			result.append(hypothesis.prettyPrint(labels)).append("\n");
		}

		return result.toString();
	}

	public String evalPartial(Statement s) {
		double last = -1;
		Hypothesis result = null;
		for (Hypothesis hypothesis : hypotheses.values()) {
			double total = hypothesis.evalPartial(s);
			if (total > last) {
				last = total;
				result = hypothesis;
			}
		}

		return result.getLabel();
	}

	public String evalTotal(Statement s) {
		for (Hypothesis hypothesis : hypotheses.values()) {
			if (hypothesis.evalTotal(s)) {
				return hypothesis.getLabel();
			}

		}

		return null;
	}

	public List<Statement> getHistory() {
		return history;
	}

	public Map<String, Hypothesis> getHypotheses() {
		return hypotheses;
	}

	public int compareTo(Theory o) {

		return steps - o.steps;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Hypothesis hypothesis : hypotheses.values()) {
			result.append(hypothesis.toString()).append("\n");
		}

		return result.toString();
	}

}
