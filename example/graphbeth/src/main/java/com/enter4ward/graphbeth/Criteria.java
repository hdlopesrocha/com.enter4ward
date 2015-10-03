package com.enter4ward.graphbeth;

import java.util.ArrayList;
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

	public void gaps(Graph graph) {
		for (Alternative a : alternatives) {
			for (Alternative b : alternatives) {
				if (a != b) {
					Judgement j = graph.get(a, b);
					if (j == null) {
						j = graph.get(b, a);
					}
					if (j == null) {
						System.out.println("need judgement between " + a.getId() + " and " + b.getId());
					}
				}
			}
		}
	}

	public boolean check() {
		boolean ans = false;
		Graph graph = new Graph(judgements);
		if (!graph.hasCycle()) {

			Map<String, Integer> variables = new TreeMap<String, Integer>();
			Solver solver = new Solver(alternatives.size());

			// #region CREATE_VARIABLES
			for (Alternative a : alternatives) {
				variables.put(a.getId(), solver.createVariable());
			}

			// #region ADD_RULES_SCALE
			for (Judgement j1 : judgements) {
				int f1 = variables.get(j1.getFrom().getId());
				int t1 = variables.get(j1.getTo().getId());

				System.out.println(j1.getFrom().getId() + " -> " + j1.getTo().getId());

				// NULL JUDGEMENT
				if (j1.getUpper() == 0 && j1.getLower() == 0) {

					solver.composeEquation(f1, 1);
					solver.composeEquation(t1, -1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.EQ, 0);
					
				
				} else if (j1.getLower() <= j1.getUpper()) {
					solver.composeEquation(f1, -1);
					solver.composeEquation(t1, 1);
					System.out.print("\t");
					solver.createEquation(ConstraintType.LE, -j1.getLower());
				}

				for (Judgement j2 : judgements) {
					if (j1 != j2) {

						int f2 = variables.get(j2.getFrom().getId());
						int t2 = variables.get(j2.getTo().getId());

						// j1 must be much better than j2, j1.low>j2.high
						if (j1.getLower() > j2.getUpper() && j1.getUpper() != 0 && j2.getUpper() != 0) {
							System.out.print("\t[" + j2.getFrom().getId() + "->" + j2.getTo().getId() + "]");
							solver.composeEquation(f1, -1);
							solver.composeEquation(t1, 1);
							solver.composeEquation(f2, 1);
							solver.composeEquation(t2, -1);
							solver.createEquation(ConstraintType.LE, j2.getUpper() - j1.getLower());
						}
					}
				}
			}

			ans = solver.solve();
			System.out.println("JUDGEMENTS");
			for (Judgement j1 : judgements) {
				int f1 = variables.get(j1.getFrom().getId());
				int t1 = variables.get(j1.getTo().getId());
				double dec = (solver.getDecision(f1) - solver.getDecision(t1));
				System.out.println(j1.getFrom().getId() + "->" + j1.getTo().getId() + " = " + dec);
			}
			System.out.println("SCALE");
			for (Alternative a : alternatives) {
				int var = variables.get(a.getId());
				double dec = (solver.getDecision(var));
				System.out.println(a.getId() + "=" + dec);
			}
		} else {
			System.out.println("Cycle detected!");
		}

		gaps(graph);
		return ans;

	}

}
