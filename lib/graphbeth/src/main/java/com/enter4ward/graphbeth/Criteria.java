package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Criteria {

	private List<Judgement> judgements = new ArrayList<Judgement>();
	private List<Alternative> alternatives = new ArrayList<Alternative>();

	public Criteria() {

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

			Map<String, Integer> variables = new TreeMap<String, Integer>();
			Solver solver = new Solver(alternatives.size()*2);

			// #region CREATE_VARIABLES
			for (Alternative a : alternatives) {
				int aLow = solver.createVariable();
				int aHigh = solver.createVariable();				
				
				variables.put(a.getId() + "-",aLow );
				variables.put(a.getId() + "+",aHigh );
				solver.composeEquation(aLow, 1);
				solver.composeEquation(aHigh, -1);
				solver.createEquation(ConstraintType.LE,0);
			}

			// #region ADD_RULES_SCALE
			for (Judgement j1 : judgements) {
				int from1Min = variables.get(j1.getFrom().getId()+"-");
				int from1Max = variables.get(j1.getFrom().getId()+"+");
				int to1Max = variables.get(j1.getTo().getId()+"+");
				int to1Min = variables.get(j1.getTo().getId()+"-");

				
				System.out.println(j1.getFrom().getId() + " -> " + j1.getTo().getId());

				// NULL JUDGEMENT
				if (j1.getMax() == 0 && j1.getMin() == 0) {
/*
					solver.composeEquation(f1, 1);
					solver.composeEquation(t1, -1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.EQ, 0);
*/
				} else {
					
		
							
					solver.composeEquation(to1Max, 1);
					solver.composeEquation(from1Max, -1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.LE, -j1.getMax());
			
					
					solver.composeEquation(to1Max, 1);
					solver.composeEquation(from1Min, -1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.LE, -j1.getMin());
		
			
					solver.composeEquation(from1Min, 1);
					solver.composeEquation(from1Max, -1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.LE, j1.getDifference());
			
				}
		


			}

			ans = solver.solve();
			Map<Alternative, Solution> scale = new TreeMap<Alternative, Solution>();

		
			System.out.println("SCALE");
			for (Alternative a : alternatives) {
				
				double decLow = (solver.getDecision(variables.get(a.getId()+"-")));
				double decHigh = (solver.getDecision(variables.get(a.getId()+"+")));
				System.out.println(a.getId() + "= [" + (float)decLow+ ","+(float)decHigh+"]");
				scale.put(a, new Solution(a, decLow, decHigh));
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
	


	
	
	public void autoComplete(Graph graph, Map<Alternative, Solution> scale) {

		
	
		

		System.out.println("SCORES");
		for (Alternative a : alternatives) {
			for (Alternative b : alternatives) {
				if (a != b) {
	
					Float min = graph.shortestPath(a, b);
					Float max = graph.longestPath(a, b);
					if(min!= null && max!=null){
						System.out.println(a.getId()+"->"+b.getId()+"="+min+"/"+max);
	
										
						Judgement j = graph.get(a, b);
						if (j == null) {
							j = graph.get(b, a);
						}
						if (j == null) {
			
							
							merge(new Judgement(JudgementType.DYNAMIC, a, b,min,max), graph, "SC1");
						}
					}
					
					
					
				}
			}
		}
		
		System.out.println("FINAL RESULT");
		for (Judgement j : judgements) {
			System.out.println(j.toString());
		}	
	}

}
