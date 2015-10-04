package com.enter4ward.graphbeth;

public class Judgement {

	private JudgementType judgementType;
	private Alternative from;
	private Alternative to;
	private int min, max;

	public Judgement(JudgementType judgementType, Alternative from, Alternative to, int value) {
		super();
		this.judgementType = judgementType;
		this.from = from;
		this.to = to;
		this.min = this.max = value;
	}

	public Judgement(JudgementType judgementType, Alternative from, Alternative to, int lower, int upper) {
		super();
		this.judgementType = judgementType;
		this.from = from;
		this.to = to;
		this.min = lower;
		this.max = upper;
	}

	public boolean isStronger(Judgement j){
		return min>j.max;
	}
	
	public int difference(Judgement j){
		return min - j.max;
	}
	
	public boolean isNull(){
		return min==0 && max == 0;
	}
	
	public String toString(){
		return from.getId()+" -> "+to.getId()+" = "+ min+"/"+max + " | "+judgementType.toString();
	}
	
	public boolean isValid(){
		return min <= max;
	}
	
	public boolean merge(Judgement j) throws MergeException {
		boolean changed = false;
		if (max != 0) {

			if (j.min > min) {
				min = j.min;
				changed |= true;
			}
			if (j.max < max) {
				max = j.max;
				changed |= true;
			}
			// XXX PROBLEMATICO XXX
			if (max < min) {
				throw new MergeException();
				// Max = bmax;
				// Min = bmin;
				// changed = false;
			}

		}
		return changed;
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
		return min;
	}

	public int getUpper() {
		return max;
	}

}
