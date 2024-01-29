package com.cronopista.ai.core;

import java.util.ArrayList;
import java.util.List;

public class Statement implements Comparable<Statement> {

	private String label;

	private List<Range> data;

	private int countHits;

	public Statement(String label) {
		this.label = label;
		data = new ArrayList<Range>();
		countHits = 1;
	}

	public Statement(String label, double... numbers) {
		this.label = label;
		data = new ArrayList<Range>();
		for (double n : numbers) {
			data.add(new Range(n));
		}
	}

	public void addHit() {
		countHits++;
	}

	public void addData(double value) {
		data.add(new Range(value));
	}

	public void addData(Range r) {
		data.add(r);
	}

	public List<Range> getData() {
		return data;
	}

	public int getCountHits() {
		return countHits;
	}

	public void setCountHits(int countHits) {
		this.countHits = countHits;
	}

	public Statement merge(Statement s) {
		Statement merged = new Statement(label);
		for (int i = 0; i < data.size(); i++) {
			merged.addData(data.get(i).merge(s.getData().get(i)));
		}
		merged.setCountHits(countHits);

		return merged;
	}

	public Statement copy() {
		Statement deepCopy = new Statement(label);
		for (int i = 0; i < data.size(); i++) {
			Range range = data.get(i);
			deepCopy.addData(new Range(range.getFrom(), range.getTo()));
		}

		return deepCopy;
	}

	public boolean evalTotal(Statement s) {
		for (int i = 0; i < data.size(); i++) {
			if (!data.get(i).eval(s.getData().get(i).getFrom())) {
				return false;
			}
		}

		return true;
	}

	public double evalPartial(Statement s) {
		int total = 0;
		int absolute = 0;
		for (int i = 0; i < data.size(); i++) {
			Range range = data.get(i);
			if (range.getFrom() > 0 || range.getTo() < 255) {
				absolute++;
				if (data.get(i).eval(s.getData().get(i).getFrom())) {
					total++;

				}
			}
		}

		return total / (double) absolute;
	}

	public String getLabel() {
		return label;
	}

	public String prettyPrint(List<String> labelNames, Statement minMax) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("( " + countHits + ": ");
		for (int i = 0; i < data.size(); i++) {
			//if (data.get(i).isRelevant(minMax.getData().get(i))) {
				if (i > 0) {
					buffer.append(" and ");
				}

				buffer.append(data.get(i).prettyPrint(labelNames.get(i)));
			//}
		}

		buffer.append(")");

		return buffer.toString();
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("( " + countHits + ": ");
		for (int i = 0; i < data.size(); i++) {
			if (i > 0) {
				buffer.append(" & ");
			}

			buffer.append(data.get(i));
		}

		buffer.append(")");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statement other = (Statement) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	public int compareTo(Statement o) {

		return countHits - o.countHits;
	}

}
