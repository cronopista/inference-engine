package com.cronopista.ai.core;

public class Range {

	private double from;

	private double to;

	public Range(double value) {
		this.from = value;
		this.to = value;
	}

	public Range(double from, double to) {
		super();
		this.from = from;
		this.to = to;
	}

	public boolean eval(double value) {
		return value >= from && value <= to;
	}

	
	public Range merge(Range r) {
		Range merged = new Range(from, to);
		if (r.getFrom() < from) {
			merged.setFrom(r.getFrom());
		}

		if (r.getTo() > to) {
			merged.setTo(r.getTo());
		}

		return merged;
	}

	public double getFrom() {
		return from;
	}

	public void setFrom(double from) {
		this.from = from;
	}

	public double getTo() {
		return to;
	}

	public void setTo(double to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return from + "-" + to;
	}

	
	public boolean isRelevant(Range minMax) {
		return !this.equals(minMax);
	}
	
	public String prettyPrint(String labelName) {
		String res = labelName;
		if (from == to) {
			res += " = " + from;
		} else {
			res += " between " + from + " and " + to;
		}

		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(from);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(to);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Range other = (Range) obj;
		if (Double.doubleToLongBits(from) != Double.doubleToLongBits(other.from))
			return false;
		if (Double.doubleToLongBits(to) != Double.doubleToLongBits(other.to))
			return false;
		return true;
	}

}
