package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lpsolve.LpSolveException;

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

	Map<String, Integer> variables = new TreeMap<String, Integer>();

	public boolean check() {
		Solver solver = new Solver(alternatives.size());

		// #region CREATE_VARIABLES
		for (Alternative a : alternatives) {
			variables.put(a.getId(), solver.AddDecision());
		}

		// #region ADD_RULES_SCALE
		for (Judgement j1 : judgements) {
			int f1 = variables.get(j1.getFrom().getId());
			int t1 = variables.get(j1.getTo().getId());

			// NULL JUDGEMENT
			if (j1.getUpper() == 0 && j1.getLower() == 0) {

				solver.AddConstraint(f1, 1);
				solver.AddConstraint(t1, -1);
				solver.AddConstraints(ConstraintType.EQ, 0);
			}
			// lol
			else if (j1.getLower() <= j1.getUpper()) {
				solver.AddConstraint(f1, 1);
				solver.AddConstraint(t1, -1);
				solver.AddConstraints(ConstraintType.GE, j1.getLower());
			}

			for (Judgement j2 : judgements) {
				int f2 = variables.get(j2.getFrom().getId());
				int t2 = variables.get(j2.getTo().getId());

				if (j1 != j2 && j1.getLower() > j2.getUpper() && j1.getUpper() != 0 && j2.getUpper() != 0) {
					solver.AddConstraint(f1, 1);
					solver.AddConstraint(t1, -1);
					solver.AddConstraint(f2, -1);
					solver.AddConstraint(t2, 1);
					solver.AddConstraints(ConstraintType.GE, j1.getLower() - j2.getUpper());
				}
			}
		}

		try {
			boolean ret = solver.Solve();

			for (Judgement j1 : judgements) {
				int f1 = variables.get(j1.getFrom().getId());
				int t1 = variables.get(j1.getTo().getId());

				double dec = (solver.GetDecision(f1) - solver.GetDecision(t1));

				System.out.println(j1.getFrom().getId() + "->" + j1.getTo().getId() + " = " + dec);
			}

			solver.WriteFile("result.txt");

			return ret;

		} catch (LpSolveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
