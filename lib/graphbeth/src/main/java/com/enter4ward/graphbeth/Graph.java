package com.enter4ward.graphbeth;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class Graph {

	private Map<Alternative, Map<Alternative, Judgement>> fromTransitions;
	private Map<Alternative, Map<Alternative, Judgement>> toTransitions;

	public Graph(List<Judgement> judgements) {
		this.fromTransitions = new TreeMap<Alternative, Map<Alternative, Judgement>>();
		this.toTransitions = new TreeMap<Alternative, Map<Alternative, Judgement>>();

		for (Judgement j : judgements) {
			add(j);
		}
	}

	public void add(Judgement j) {
		{
			Map<Alternative, Judgement> fts = fromTransitions.get(j.getFrom());
			if (fts == null) {
				fts = new TreeMap<Alternative, Judgement>();
				fromTransitions.put(j.getFrom(), fts);
			}
			fts.put(j.getTo(), j);
		}
		{
			Map<Alternative, Judgement> tts = toTransitions.get(j.getTo());
			if (tts == null) {
				tts = new TreeMap<Alternative, Judgement>();
				toTransitions.put(j.getTo(), tts);
			}
			tts.put(j.getFrom(), j);
		}
	}

	public Judgement get(Alternative from, Alternative to) {
		Map<Alternative, Judgement> a = fromTransitions.get(from);
		if (a != null) {
			return a.get(to);
		}
		return null;
	}

	public Collection<Judgement> getTo(Alternative a) {
		Map<Alternative, Judgement> map = toTransitions.get(a);
		return map != null ? map.values() : null;
	}

	public Collection<Judgement> getFrom(Alternative a) {
		Map<Alternative, Judgement> map = fromTransitions.get(a);
		return map != null ? map.values() : null;
	}

	public Collection<Alternative> getFromAlternatives() {
		return fromTransitions.keySet();
	}

	public Collection<Alternative> getToAlternatives() {
		return toTransitions.keySet();
	}

	private boolean hasCycle(Alternative current, Set<Alternative> processing, Set<Alternative> visited) {
		Collection<Judgement> ts = getFrom(current);
		visited.add(current);
		processing.add(current);
		boolean ans = false;

		if (ts != null) {
			for (Judgement j : ts) {
				if (processing.contains(j.getTo())) {
					return true;
				}
				if (!visited.contains(j.getTo())) {
					ans |= hasCycle(j.getTo(), processing, visited);
					if (ans) {
						break;
					}
				}
			}
		}
		processing.remove(current);

		return ans;
	}

	public boolean hasCycle() {
		boolean ans = false;
		Set<Alternative> visited = new HashSet<Alternative>();
		for (Alternative a : getFromAlternatives()) {
			Set<Alternative> processing = new HashSet<Alternative>();
			if (!visited.contains(a)) {
				ans |= hasCycle(a, processing, visited);
				if (ans) {
					break;
				}
			}
		}
		return ans;
	}

	private Float shortestPathAux(Alternative current, Alternative to, float cost) {
		Float bestDistance = null;

		Map<Alternative, Judgement> judgements = fromTransitions.get(current);
		if (judgements != null) {
			for (Judgement j : judgements.values()) {
				Float attempt = cost + j.getMax();
				if (!j.getTo().equals(to)) {					
					attempt = shortestPathAux(j.getTo(), to, attempt);
				}
				if (attempt != null && (bestDistance == null || attempt < bestDistance)) {
					bestDistance = attempt;
				}
			}
		}
		return bestDistance;
	}

	public Float shortestPath(Alternative from, Alternative to) {
		return shortestPathAux(from, to, 0);
	}
	
	
	private Float longestPathAux(Alternative current, Alternative to, float cost) {
		Float bestDistance = null;

		Map<Alternative, Judgement> judgements = fromTransitions.get(current);
		if (judgements != null) {
			for (Judgement j : judgements.values()) {
				Float attempt = cost + j.getMax();
				if (!j.getTo().equals(to)) {					
					attempt = longestPathAux(j.getTo(), to, attempt);
				}
				if (attempt != null && (bestDistance == null || attempt > bestDistance)) {
					bestDistance = attempt;
				}
			}
		}
		return bestDistance;
	}

	public Float longestPath(Alternative from, Alternative to) {
		return longestPathAux(from, to, 0);
	}
}
