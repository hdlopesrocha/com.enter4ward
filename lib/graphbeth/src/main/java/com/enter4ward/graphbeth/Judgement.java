package com.enter4ward.graphbeth;

public class Judgement {
	
	private JudgementType judgementType;
	private Alternative from;
	private Alternative to;
	private int lower, upper;

	public Judgement(JudgementType judgementType, Alternative from, Alternative to, int value) {
		super();
		this.judgementType = judgementType;
		this.from = from;
		this.to = to;
		this.lower = this.upper = value;
	}

	public Judgement(JudgementType judgementType, Alternative from, Alternative to, int lower, int upper) {
		super();
		this.judgementType = judgementType;
		this.from = from;
		this.to = to;
		this.lower = lower;
		this.upper = upper;
	}
	
	public JudgementType getJudgementType() {
		return judgementType;
	}
	
	public Alternative getFrom() {
		return from;
	}
	
	public Alternative getTo() {
		return to;
	}

	public int getLower() {
		return lower;
	}

	public int getUpper() {
		return upper;
	}

	

}
