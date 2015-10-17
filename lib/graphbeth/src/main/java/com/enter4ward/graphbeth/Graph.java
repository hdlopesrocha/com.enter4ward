package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
		return map != null ? map.values() : new ArrayList<Judgement>();
	}

	public Collection<Judgement> getFrom(Alternative a) {
		Map<Alternative, Judgement> map = fromTransitions.get(a);
		return map != null ? map.values() : new ArrayList<Judgement>();
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

	private Paths extremePathsAux(Alternative current, Alternative goal, Interval cost, Judgement except,
			Set<Alternative> reachable, LinkedList<Judgement> path) {
		Paths solution = null;
		for (Judgement j : getFrom(current)) {

			if (j != except) {
				path.push(j);
				Interval t = new Interval(cost).add(j.getInterval());
				Interval recursion = null;
				if (!j.getTo().equals(goal)) {
					if (!reachable.contains(j.getTo())) {
						recursion = pathSizeAux(j.getTo(), goal, t, except, reachable);
					}
				} else {
					recursion = t;
				}

				if (recursion != null) {
					if (solution == null) {
						solution = new Paths(recursion, path);
					} else {
						if (recursion.getMax() > solution.getMax()) {
							solution.setMax(recursion.getMax());
							solution.setMaxPath(path);
						}
						if (recursion.getMin() < solution.getMin()) {
							solution.setMin(recursion.getMin());
							solution.setMinPath(path);
						}
					}
				}
				path.pop();
			}
		}
		return solution;
	}

	private Interval pathSizeAux(Alternative current, Alternative goal, Interval cost, Judgement except,
			Set<Alternative> reachable) {
		Interval solution = null;
		for (Judgement j : getFrom(current)) {
			if (j != except) {
				Interval t = new Interval(cost).add(j.getInterval());
				Interval recursion = null;
				if (!j.getTo().equals(goal)) {
					if (!reachable.contains(j.getTo())) {
						recursion = pathSizeAux(j.getTo(), goal, t, except, reachable);
					}
				} else {
					recursion = t;
				}

				if (recursion != null) {
					if (solution == null) {
						solution = new Interval(recursion);
					} else {
						if (recursion.getMax() > solution.getMax()) {
							solution.setMax(recursion.getMax());
						}
						if (recursion.getMin() < solution.getMin()) {
							solution.setMin(recursion.getMin());
						}
					}
				}
			}
		}
		return solution;
	}

	private boolean enforceAux(Alternative current, Alternative goal, Interval remainder, Judgement except,
			Set<Alternative> reachable) {
		boolean found = false;
		for (Judgement j : getFrom(current)) {
			if (j != except && !reachable.contains(j.getTo())) {
				if (j.getTo().equals(goal)) {
					found = true;
				} else {
					found |= enforceAux(j.getTo(), goal, remainder, except, reachable);
				}

				if (found) {
					Interval i = new Interval(j.getInterval()).add(remainder);
					System.out.println(j.toString() + " ### " + i.toString());

					j.addSuggestion(i);
				}

			}
		}
		return found;
	}

	private Set<Alternative> calculateReachable(Alternative alt, Set<Alternative> reachable) {
		Collection<Judgement> judgements = getFrom(alt);
		for (Judgement j : judgements) {
			if (!reachable.contains(j.getTo())) {
				reachable.add(j.getTo());
				calculateReachable(j.getTo(), reachable);
			}
		}
		return reachable;
	}

	public void enforce() {
		
		for(Alternative to : getToAlternatives()){
			Set<Alternative> reachable = calculateReachable(to, new HashSet<Alternative>());
			for(Alternative from : getFromAlternatives()){
				Judgement judgement = get(from, to);
				if(judgement!=null){
					Paths s = extremePathsAux(from, to, new Interval(0, 0), judgement, reachable,
							new LinkedList<Judgement>());
					if (s != null) {
						System.out.println("%%%"+ judgement.toString() + " $$$ " +s.toString());
						float d = s.getDifference(); 
						if (d>0) {

							for (Judgement j : s.getMinPath()) {
								Interval i = new Interval(j.getInterval()).add(new Interval(d,0));
								if(i.getMin()>i.getMax()){
									i.setMax(i.getMin());
								}
								System.out.println(j.toString() + " ### " + i.toString() +"|"+d);
								j.addSuggestion(i);
							}

							for (Judgement j2 : s.getMaxPath()) {
								Interval i = new Interval(j2.getInterval()).subtract(new Interval(0,d));
								if(i.getMax()<i.getMin()){
									i.setMin(i.getMax());
								}
								System.out.println(j2.toString() + " ### " + i.toString()+"|"+d);
								j2.addSuggestion(i);
							}

						//	enforceAux(j.getFrom(), j.getTo(), remainder, j, reachable);
						}
					}					
				}
			}	
		}
	}

	public Interval pathSize(Alternative from, Alternative to, Judgement exc) {
		Set<Alternative> reachable = calculateReachable(to, new HashSet<Alternative>());
		return pathSizeAux(from, to, new Interval(0, 0), exc, reachable);
	}
}
