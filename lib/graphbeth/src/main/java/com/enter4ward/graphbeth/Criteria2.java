package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Criteria2 {

	private List<Judgement> judgements = new ArrayList<Judgement>();
	private List<Alternative> alternatives = new ArrayList<Alternative>();
	private static final float EPSILON = 0.00001f;

	public Criteria2() {

	}

	public void addAlternative(Alternative alternative) {
		alternatives.add(alternative);
	}

	public void addJudgement(Judgement judgement) {
		judgements.add(judgement);
	}


	public boolean check() {
		boolean ans = false;
		Graph graph = new Graph(judgements);
		if (!graph.hasCycle()) {

			Map<Alternative, Integer> variables = new TreeMap<Alternative, Integer>();
			Solver solver = new Solver(alternatives.size());

			// #region CREATE_VARIABLES
			for (Alternative a : alternatives) {
				variables.put(a, solver.createVariable());
			}

			// #region ADD_RULES_SCALE
			for (Judgement j1 : judgements) {
				int f1 = variables.get(j1.getFrom());
				int t1 = variables.get(j1.getTo());

				System.out.println(j1.getFrom().getId() + " -> " + j1.getTo().getId());

				// NULL JUDGEMENT
				if (j1.getMax() == 0 && j1.getMin() == 0) {

					solver.composeEquation(f1, 1);
					solver.composeEquation(t1, -1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.EQ, 0);

				} else if (j1.getMin() <= j1.getMax()) {
					solver.composeEquation(f1, -1);
					solver.composeEquation(t1, 1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.LE, -j1.getMin());
				}

				for (Judgement j2 : judgements) {
					if (j1 != j2) {

						int f2 = variables.get(j2.getFrom());
						int t2 = variables.get(j2.getTo());

						// j1 must be much better than j2, j1.low>j2.high
						if (j1.getMin() > j2.getMax() && j1.getMax() != 0 && j2.getMax() != 0) {
							System.out.print("\t[" + j2.getFrom().getId() + "->" + j2.getTo().getId() + "]");
							solver.composeEquation(f1, -1);
							solver.composeEquation(t1, 1);
							solver.composeEquation(f2, 1);
							solver.composeEquation(t2, -1);
							solver.createEquation(ConstraintType.LE, j2.getMax() - j1.getMin());
						}
					}
				}
			}

			ans = solver.solve();
			Map<Alternative, Float> scale = new TreeMap<Alternative, Float>();

		
			System.out.println("SCALE");
			for (Alternative a : alternatives) {
				int var = variables.get(a);
				float dec = (float)(solver.getDecision(var));
				System.out.println(a.getId() + "=" + dec);
				scale.put(a, dec);
			}

			autoComplete(graph,scale);
			
		} else {
			System.out.println("Cycle detected!");
		}


		return ans;

	}

	private boolean merge(Judgement j, Graph graph, String rule) {
		boolean changed = false;
		Judgement k = graph.get(j.getFrom(), j.getTo());

		if (k == null) {
			addJudgement(j);
			graph.add(j);
			changed |= true;
		} else if (k.getJudgementType() == JudgementType.DYNAMIC) {
			try {
				changed |= k.merge(j);
			} catch (MergeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (changed) {
			System.out.println(rule + " | " + j.toString());
		}

		return changed;
	}
	

	private void startRule(Alternative a, Graph graph) {
		Collection<Judgement> judgements = graph.getFrom(a);

		for (Judgement j1 : judgements) // [x,y]
		{
			for (Judgement j2 : judgements) // [z,w]
			{
				if (j1 != j2 && j1.getJudgementType().equals(JudgementType.FIXED)
						&& j2.getJudgementType().equals(JudgementType.FIXED)) {
					// NULL RULE
					if (j2.isNull()) {
						Judgement j = new Judgement(JudgementType.DYNAMIC, j2.getTo(), j1.getTo(), j1.getMin(),
								j1.getMax());
						merge(j, graph, "SR1");
					}
					// DIFFERENT ONES
					else if (j1.isStronger(j2)) {
						Judgement j = new Judgement(JudgementType.DYNAMIC, j2.getTo(), j1.getTo(), j1.difference(j2),
								Float.MAX_VALUE);
						merge(j, graph, "SR2");
					}
				}
			}
		}
	}
	
	public void scoreCompleter(Graph graph,Map<Alternative, Float> scale){
		for (Alternative a : alternatives) {
			for (Alternative b : alternatives) {
				if (a != b) {
					Judgement j = graph.get(a, b);
					if (j == null) {
						j = graph.get(b, a);
					}
					if (j == null) {
						float dif = scale.get(a)-scale.get(b);
						if(dif>1){
							merge(new Judgement(JudgementType.DYNAMIC, a, b,1f,dif), graph, "SC1");
						}
						else if(dif<-1) {
							merge(new Judgement(JudgementType.DYNAMIC, b, a,1f,-dif), graph, "SC2");							
						}
					}
				}
			}
		}	
	}
	
	
	public void autoComplete(Graph graph, Map<Alternative, Float> scale) {
		scoreCompleter(graph, scale);
		
		/*
		for (Alternative a : graph.getFromAlternatives()) {
			pathRule(a, graph);
		}
*/
		for (Alternative a : graph.getFromAlternatives()) {
			startRule(a, graph);
		}
/*
		for(Judgement j : new ArrayList<Judgement>(judgements)){
			if(!j.isValid()){
				judgements.remove(j);
			}
		}
		*/
		
		System.out.println("FINAL RESULT");
		for (Judgement j : judgements) {
			System.out.println(j.toString());
		}
	}

}
