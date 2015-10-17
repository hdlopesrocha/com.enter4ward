package com.enter4ward.graphbeth;

import java.util.LinkedList;
import java.util.Stack;

public class Paths extends Interval {
	
	private LinkedList<Judgement> minPath;
	private LinkedList<Judgement> maxPath;
		
	
	public LinkedList<Judgement> getMinPath() {
		return minPath;
	}

	public void setMinPath(LinkedList<Judgement> p) {
		this.minPath = new LinkedList<Judgement>(p);
	}

	public LinkedList<Judgement> getMaxPath() {
		return maxPath;
	}

	public void setMaxPath(LinkedList<Judgement> p) {
		this.maxPath = new LinkedList<Judgement>(p);
	}

	public Paths(float min, float max) {
		super(min,max);
	}

	public Paths(Interval i,LinkedList<Judgement> p) {
		super(i);
		minPath = maxPath = new LinkedList<Judgement>(p);
	}

	
	
	
}
