package com.enter4ward.graphbeth;

public class Solution implements Comparable<Solution> {
	
	private Alternative alternative;
	double min;
	double max;
	
	
	public Solution(Alternative alternative, double min, double max) {
		super();
		this.alternative = alternative;
		this.min = min;
		this.max = max;
	}
	public Alternative getAlternative() {
		return alternative;
	}
	public double getMin() {
		return min;
	}
	public double getMax() {
		return max;
	}
	
	@Override
	public int compareTo(Solution o) {
		return alternative.compareTo(o.alternative);
	}
	@Override
	public int hashCode() {
		return alternative.hashCode();
	}
	

	
}
