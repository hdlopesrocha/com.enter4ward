package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

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

	public JSONObject check() {
		Graph graph = new Graph(judgements);
		JSONObject result = new JSONObject();

		if (!graph.hasCycle()) {

			Map<String, Integer> variables = new TreeMap<String, Integer>();
			Solver solver = new Solver(alternatives.size() * 2);

			// #region CREATE_VARIABLES
			for (Alternative a : alternatives) {
				int aMin = solver.createVariable();
				int aMax = solver.createVariable();

				variables.put(a.getId() + "-", aMin);
				variables.put(a.getId() + "+", aMax);

				// solver.composeEquation(aMin, 1);
				// solver.composeEquation(aMax, -1);
				// solver.createEquation(ConstraintType.LE, 0);
			}

			System.out.println("SCORES");
			for (Alternative a : alternatives) {
				for (Alternative b : alternatives) {
					if (a != b) {

						Float min = graph.shortestPath(a, b);
						Float max = graph.longestPath(a, b);
						if (min != null && max != null) {
							System.out.println(a.getId() + "->" + b.getId() + "=" + min + "/" + max);
							int aMin = variables.get(a.getId() + "-");
							int aMax = variables.get(a.getId() + "+");
							int bMax = variables.get(b.getId() + "+");

							solver.composeEquation(aMin, -1);
							solver.composeEquation(bMax, 1);
							solver.createEquation(ConstraintType.LE, -min);
							solver.composeEquation(aMax, -1);
							solver.composeEquation(bMax, 1);
							solver.createEquation(ConstraintType.LE, -max);
							solver.composeEquation(aMin, 1);
							solver.composeEquation(aMax, -1);
							solver.createEquation(ConstraintType.LE, min - max);

							Judgement j = graph.get(a, b);
							if (j == null) {
								j = graph.get(b, a);
							}
							if (j == null) {

								merge(new Judgement(JudgementType.DYNAMIC, a, b, min, max), graph, "SC1");
							}
						}

					}
				}
			}

			System.out.println("FINAL RESULT");
			for (Judgement j : judgements) {
				System.out.println(j.toString());
			}
			
			JSONObject judgements = new JSONObject();
			
			
			
			if (solver.solve()) {

				System.out.println("SCALE");

				JSONObject scale = new JSONObject();
				for (Alternative a : alternatives) {
					// Building judgements
					JSONObject fObj = new JSONObject();
					for(Judgement j : graph.getFrom(a)){
						JSONObject tObj = new JSONObject();
						tObj.put("min", j.getMin());
						tObj.put("max", j.getMax());
						tObj.put("type", j.getJudgementType());
						fObj.put(j.getTo().getId().toString(), tObj);
					}
					judgements.put(a.getId().toString(), fObj);
					
					
					
					// Building scale
					double decLow = (solver.getDecision(variables.get(a.getId() + "-")));
					double decHigh = (solver.getDecision(variables.get(a.getId() + "+")));
					JSONArray array = new JSONArray();
					array.put(decLow);
					array.put(decHigh);
					scale.put(a.getId(), array);
				}
				result.put("scale", scale);
				result.put("judgements", judgements);
				
			}
			

		} else {
			System.out.println("Cycle detected!");
		}

		return result;

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

}
