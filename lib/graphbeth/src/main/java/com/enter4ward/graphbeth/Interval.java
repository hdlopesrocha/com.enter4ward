package com.enter4ward.graphbeth;

public class Interval  {
	
	private float min;
	private float max;
	
	
	public Interval(float min, float max) {
		super();
		this.min = min;
		this.max = max;
	}

	public Interval(Interval i) {
		super();
		this.min = i.min;
		this.max = i.max;
	}
	
	public float getMin() {
		return min;
	}
	public float getMax() {
		return max;
	}

	public void setMax(float x) {
		this.max = x;
	}
	
	public String toString(){
		return getMin() + "/" + getMax();
	}
	
	public boolean isNull() {
		return min == 0 && max == 0;
	}
	
	public void setMin(float x) {
		this.min = x;
	}
	
	public Interval add(Interval i){
		max+=i.max;
		min+=i.min;
		return this;
	}
	

	public boolean equals(Interval obj) {
		return min == obj.getMin() && max == obj.getMax();
	}

	public Interval subtract(Interval i){
		max-=i.max;
		min-=i.min;
		return this;
	}
}
