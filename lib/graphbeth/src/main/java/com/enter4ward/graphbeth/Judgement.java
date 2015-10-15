package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.List;

public class Judgement {

	private JudgementType judgementType;
	private Alternative from;
	private Alternative to;
	private Interval interval;
	private List<Interval> suggestions = new ArrayList<Interval>();

	public Judgement(JudgementType judgementType, Alternative from, Alternative to, Float value) {
		super();
		this.judgementType = judgementType;
		this.from = from;
		this.to = to;
		this.interval = new Interval(value, value);
	}

	public Judgement(JudgementType judgementType, Alternative from, Alternative to, Float min, Float max) {
		super();
		this.judgementType = judgementType;
		this.from = from;
		this.to = to;
		this.interval = new Interval(min, max);

	}

	public void addSuggestion(Interval s) {
		if (!interval.equals(s) && s.getMax()>=s.getMin() && s.getMin()>0) {

			for (Interval i : suggestions) {
				if (i.equals(s)) {
					return;
				}
			}

			suggestions.add(s);
		}
	}

	public boolean isStronger(Judgement j) {
		return getMin() > j.getMax();
	}

	public Float difference(Judgement j) {
		return getMin() - j.getMax();
	}

	public String toString() {
		return from.getId() + " -> " + to.getId() + " = " + interval.toString() + " | " + judgementType.toString();
	}

	public boolean isValid() {
		return getMin() <= getMax();
	}

	public boolean merge(Judgement j) throws MergeException {
		boolean changed = false;
		if (getMax() != 0) {

			if (j.getMin() > getMin()) {
				interval.setMin(j.getMin());
				changed |= true;
			}
			if (j.getMax() < getMax()) {
				interval.setMax(j.getMax());
				changed |= true;
			}
			// XXX PROBLEMATICO XXX
			if (getMax() < getMin()) {
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

	public Float getMin() {
		return interval.getMin();
	}

	public Float getMax() {
		return interval.getMax();
	}

	public Float getDifference() {
		return getMax() - getMin();
	}

	public Interval getInterval() {
		return interval;
	}

	public List<Interval> getSuggestions() {
		return suggestions;
	}

}
