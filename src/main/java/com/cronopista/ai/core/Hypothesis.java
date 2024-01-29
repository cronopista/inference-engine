package com.cronopista.ai.core;

import java.util.ArrayList;
import java.util.List;

public class Hypothesis {

	private String label;

	private Statement maxMin;

	
	private List<Statement> statements;

	public Hypothesis(String label) {
		this.label = label;
		statements = new ArrayList<Statement>();
	}

	public void addStatement(Statement s) {
		statements.add(s);
		addToMaxMin(s);
	}

	private void addToMaxMin(Statement s) {
		if (maxMin == null) {
			maxMin = s.copy();
		}

		for (int i = 0; i < maxMin.getData().size(); i++) {
			Range maxMinRange = maxMin.getData().get(i);
			Range sRange = s.getData().get(i);
			if (sRange.getFrom() < maxMinRange.getFrom()) {
				maxMinRange.setFrom(sRange.getFrom());
			}
			if (sRange.getTo() > maxMinRange.getTo()) {
				maxMinRange.setTo(sRange.getTo());
			}

		}

	}

	
	public Hypothesis mergeToStatement(int index, Statement s) {
		Hypothesis t = new Hypothesis(label);
		for (int i = 0; i < statements.size(); i++) {
			Statement statement = statements.get(i);
			if (i == index) {
				statement = statement.merge(s);
			}
			t.addStatement(statement);
		}

		return t;
	}

	public boolean isConsistent(Statement s) {
		for (Statement part : statements) {
			if (part.evalTotal(s)) {
				return s.getLabel().equals(label);
			}
		}

		return !s.getLabel().equals(label);
	}

	public boolean evalTotal(Statement s) {
		for (Statement part : statements) {
			if (part.evalTotal(s)) {
				return true;
			}
		}

		return false;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

	public String getLabel() {
		return label;
	}

	public String prettyPrint(List<String> labelNames) {
		StringBuilder buffer = new StringBuilder();
		for (Statement s : statements) {
			if (buffer.length() > 0) {
				buffer.append(" or ");
			}

			buffer.append(s.prettyPrint(labelNames, maxMin));
		}

		return label + buffer.toString();
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for (Statement s : statements) {
			if (buffer.length() > 0) {
				buffer.append(" || ");
			}

			buffer.append(s);
		}

		return label + buffer.toString();
	}

	public double evalPartial(Statement s) {
		double total = 0;
		for (Statement part : statements) {
			double matches = part.evalPartial(s);
			if (matches > total) {
				total = matches;
			}
		}

		return total;
	}

}
