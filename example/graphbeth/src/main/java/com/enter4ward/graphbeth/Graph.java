package com.enter4ward.graphbeth;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Graph {

	private Map<Alternative, Map<Alternative,Judgement>> transitions;
	
	public Graph(List<Judgement> judgements){
		this.transitions = new TreeMap<Alternative, Map<Alternative,Judgement>>();
		for (Judgement j : judgements) {
			Map<Alternative,Judgement> ts = transitions.get(j.getFrom());
			if (ts == null) {
				ts = new TreeMap<Alternative,Judgement>();
				transitions.put(j.getFrom(), ts);
			}
			ts.put(j.getTo(),j);
		}
	}
		
	public Judgement get(Alternative from, Alternative to){
		Map<Alternative, Judgement> a = transitions.get(from);
		if(a!=null){
			return a.get(to);
		}
		return null;
	}
	
	
	private boolean hasCycle(Alternative current, Set<Alternative> processing,
			Map<Alternative, Map<Alternative,Judgement>> transitions, Set<Alternative> visited) {
		Map<Alternative,Judgement> ts = transitions.get(current);
		visited.add(current);
		processing.add(current);
		boolean ans = false;

		if (ts != null) {
			for (Judgement j : ts.values()) {
				if (processing.contains(j.getTo())) {
					return true;
				}
				if (!visited.contains(j.getTo())) {
					ans |= hasCycle(j.getTo(), processing, transitions, visited);
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
		for (Entry<Alternative, Map<Alternative, Judgement>> a : transitions.entrySet()) {
			Set<Alternative> processing = new HashSet<Alternative>();
			if (!visited.contains(a)) {
				ans |= hasCycle(a.getKey(), processing, transitions, visited);
				if (ans) {
					break;
				}
			}
		}

		return ans;
	}
	
}
